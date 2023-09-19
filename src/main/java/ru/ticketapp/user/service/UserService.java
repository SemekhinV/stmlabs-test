package ru.ticketapp.user.service;

import ru.ticketapp.user.dto.UserDto;
import ru.ticketapp.user.model.User;

public interface UserService {

    UserDto getUser(Long id);

    UserDto addUser(UserDto user);

    User getUser(String login);

    void checkRole(Long userId);
}
