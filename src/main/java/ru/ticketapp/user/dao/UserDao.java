package ru.ticketapp.user.dao;

import ru.ticketapp.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> save(User user);

    Optional<User> get(Long id);

    List<User> findUserByLogin(String login);
}
