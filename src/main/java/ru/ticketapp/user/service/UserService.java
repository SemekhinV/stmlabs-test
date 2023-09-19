package ru.ticketapp.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ticketapp.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUser(Long id);

    UserDto addUser(UserDto user);
}
