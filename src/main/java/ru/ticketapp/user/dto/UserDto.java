package ru.ticketapp.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Name of new user")
    String name;

    @Schema(description = "User login (unique)")
    String login;

    @Schema(description = "User password")
    String password;
}
