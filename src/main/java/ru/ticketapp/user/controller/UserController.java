package ru.ticketapp.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ticketapp.user.dto.UserDto;
import ru.ticketapp.user.service.UserService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Tag(name = "User controller", description = "user creation controller")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create new user"
    )
    @PostMapping()
    public UserDto saveUser(@RequestBody @Parameter(description = "User data") @Valid UserDto user) {

        return userService.addUser(user);
    }
}
