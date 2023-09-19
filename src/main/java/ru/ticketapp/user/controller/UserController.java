package ru.ticketapp.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.user.dto.UserDto;
import ru.ticketapp.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserDto saveUser(@RequestBody @Valid UserDto user) {

        return userService.addUser(user);
    }
}
