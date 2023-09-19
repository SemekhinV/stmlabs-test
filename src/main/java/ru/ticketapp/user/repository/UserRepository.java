package ru.ticketapp.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import ru.ticketapp.user.model.User;

import java.util.List;
import java.util.Optional;

import static ru.ticketapp.jooq.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    public Long save(User user) {

        return dsl.insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.LOGIN, user.getLogin())
                .set(USERS.PASSWORD, user.getPassword())
                .returning(USERS.ID)
                .fetchOne()
                .getId();
    }

    public User get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(USERS, USERS.ID.eq(id))
        );

        return records.map(
                record -> User.builder()
                        .id(record.getId())
                        .name(record.getName())
                        .login(record.getLogin())
                        .password(record.getPassword())
                        .build())
                .orElse(null);
    }

    public List<User> findByLogin(String login) {

        var records = dsl.fetch(USERS, USERS.LOGIN.contains(login)).sortAsc(USERS.ID);

        return records.map(
                        record -> User.builder()
                                .id(record.getId())
                                .name(record.getName())
                                .login(record.getLogin())
                                .password(record.getPassword())
                                .build());

//        return dsl.selectFrom(USERS)
//                .where(USERS.LOGIN.eq(login))
//                .fetch()
//                .into(User.class);
    }
}
