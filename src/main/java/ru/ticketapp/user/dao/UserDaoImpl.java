package ru.ticketapp.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ticketapp.user.mapper.UserMapper;
import ru.ticketapp.user.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public Optional<User> save(User user) {

        jdbcTemplate.update(
                "INSERT INTO users (id, name, login, password) VALUES (?,?,?,?,?)",
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword());

        return Optional.of(user);
    }

    public Optional<User> get(Long id) {

        SqlRowSet userRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM users WHERE id = ?", id);

        if (userRow.next()) {

            User user = User.builder()
                    .name(userRow.getString("name"))
                    .login(Objects.requireNonNull(userRow.getString("login")))
                    .password(Objects.requireNonNull(userRow.getString("password")))
                    .build();

            return Optional.of(user);
        } else {

            return Optional.empty();
        }
    }

    public List<User> findUserByLogin(String login) {

        return jdbcTemplate.query(
                "SELECT u FROM User u WHERE u.login = ?",
                 (rs, rowNum) -> UserMapper.toUser(rs), login);
    }
}
