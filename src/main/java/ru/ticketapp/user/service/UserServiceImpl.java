package ru.ticketapp.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.ticketapp.exception.validation.EntityNotFoundException;
import ru.ticketapp.exception.validation.InvalidValueException;
import ru.ticketapp.exception.validation.LoginException;
import ru.ticketapp.user.dto.UserDto;
import ru.ticketapp.user.mapper.UserMapper;
import ru.ticketapp.user.model.User;
import ru.ticketapp.user.repository.UserRepository;

import static ru.ticketapp.user.mapper.UserMapper.toUserDto;


@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserService{

    private final UserRepository repository;

    private void isValid(UserDto userDto) {

        if (userDto.getName() == null || userDto.getLogin() == null || userDto.getPassword() == null) {
            throw new InvalidValueException("Ошибка валидации, передан один или несколько пустых параметров.");
        }
    }

    @Override
    public UserDto addUser(UserDto user) {

        isValid(user);

        try {

            user.setId(repository.save(UserMapper.toUser(user)));

            user.setPassword("**************");

            return user;

        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException) {

                throw new LoginException("Пользователь с логином " + user.getLogin() + " уже существует.");
            }

            throw new InvalidValueException(e.getMessage());
        }
    }

    @Override
    public UserDto getUser(Long id) {

        User user = repository.get(id);

        if (user == null) {
            throw new EntityNotFoundException("Ошибка поиска пользователя, запись с id = " + id + " не найдена.");
        }

        return toUserDto(user);
    }
}
