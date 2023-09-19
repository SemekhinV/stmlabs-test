package ru.ticketapp.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ticketapp.jwt.JwtAuthentication;
import ru.ticketapp.jwt.JwtProvider;
import ru.ticketapp.jwt.JwtRequest;
import ru.ticketapp.jwt.JwtResponse;
import ru.ticketapp.user.model.User;
import ru.ticketapp.user.service.UserService;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {

        final User user = userService.getUser(authRequest.getLogin());

        if (user.getPassword().equals(authRequest.getPassword())) {

            final String accessToken = jwtProvider.generateAccessToken(user);

            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getLogin(), refreshToken);

            return new JwtResponse(accessToken, refreshToken);
        } else {

            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);

            final String login = claims.getSubject();

            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final User user = userService.getUser(login);

                final String accessToken = jwtProvider.generateAccessToken(user);

                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);

            final String login = claims.getSubject();

            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final User user = userService.getUser(login);

                final String accessToken = jwtProvider.generateAccessToken(user);

                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(user.getLogin(), newRefreshToken);

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
