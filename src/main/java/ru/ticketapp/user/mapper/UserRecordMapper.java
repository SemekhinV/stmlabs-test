package ru.ticketapp.user.mapper;

import lombok.RequiredArgsConstructor;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.ticketapp.jooq.tables.records.UsersRecord;
import ru.ticketapp.user.model.User;

@Component
@RequiredArgsConstructor
public class UserRecordMapper implements RecordMapper<UsersRecord, User> {

    @Override
    public User map(UsersRecord record) {

        return User.builder()
                .id(record.getId())
                .name(record.getName())
                .login(record.getLogin())
                .build();
    }
}
