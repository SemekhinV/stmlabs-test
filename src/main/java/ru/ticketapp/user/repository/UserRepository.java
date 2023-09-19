package ru.ticketapp.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.ticketapp.user.mapper.UserRecordMapper;
import ru.ticketapp.user.model.User;

import java.util.List;
import java.util.Optional;

import static ru.ticketapp.jooq.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    private final UserRecordMapper userRecordMapper;

    public Long save(User user) {

        return dsl.insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.LOGIN, user.getLogin())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.ROLE, user.getRole().name())
                .returning(USERS.ID)
                .fetchOne()
                .getId();
    }

    public User get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(USERS, USERS.ID.eq(id))
        );

        return records.map(userRecordMapper::map).orElse(null);
    }

    public User get(String login) {

        var records = Optional.ofNullable(
                dsl.fetchOne(USERS, USERS.LOGIN.eq(login))
        );

        return records.map(userRecordMapper::map).orElse(null);
    }

    public List<User> findByLogin(String login) {

        var records = dsl.fetch(USERS, USERS.LOGIN.contains(login)).sortAsc(USERS.ID);

        return records.map(userRecordMapper::map);
    }

    public Long checkUserRole(Long userId) {

        var records = dsl.fetchOne(USERS, USERS.ID.eq(userId), USERS.ROLE.eq("ADMIN"));

        return records == null ? -1L : 1L;
    }
}
