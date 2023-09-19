package ru.ticketapp.user.service;

import ru.ticketapp.user.dto.UserDto;

public interface UserService {

    UserDto getUser(Long id);

    UserDto addUser(UserDto user);
}
