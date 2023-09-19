package ru.ticketapp.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ticketapp.user.dto.UserDto;
import ru.ticketapp.user.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toUserDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .build();
    }

    public static User toUser(UserDto userDto) {

        return new User(userDto.getId() ,userDto.getName(), userDto.getLogin(), userDto.getPassword());
    }

    public static User toUser(ResultSet user) throws SQLException {

        return User.builder()
                .id(user.getLong("id"))
                .name(user.getString("name"))
                .login(user.getString("login"))
                .password(user.getString("password"))
                .build();
    }
}
