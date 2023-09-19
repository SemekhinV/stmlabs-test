package ru.ticketapp.carrier.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.ticketapp.carrier.model.Carrier;

import java.util.List;
import java.util.Optional;

import static ru.ticketapp.jooq.tables.Carriers.CARRIERS;

@Repository
@RequiredArgsConstructor
public class CarrierRepository {

    private final DSLContext dsl;

    public Long save(Carrier carrier) {

        return dsl.insertInto(CARRIERS)
                .set(CARRIERS.NAME, carrier.getName())
                .set(CARRIERS.PHONE_NUMBER, carrier.getPhoneNumber())
                .returning(CARRIERS.ID)
                .fetchOne()
                .getId();
    }

    public Carrier get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(CARRIERS, CARRIERS.ID.eq(id))
        );

        return records.map(
                record -> Carrier.builder()
                        .id(record.getId())
                        .name(record.getName())
                        .phoneNumber(record.getPhoneNumber())
                        .build())
                .orElse(null);

//        return Optional.of(
//                dsl.selectFrom(CARRIERS)
//                        .where(CARRIERS.ID.eq(id))
//                        .fetchAny()
//                        .into(Carrier.class));
    }

    public Carrier get(String name) {

        var records = Optional.ofNullable(
                dsl.fetchOne(CARRIERS, CARRIERS.NAME.eq(name))
        );

        return records.map(
                        record -> Carrier.builder()
                                .id(record.getId())
                                .name(record.getName())
                                .phoneNumber(record.getPhoneNumber())
                                .build())
                .orElse(null);

//        return Optional.of(
//                dsl.selectFrom(CARRIERS)
//                        .where(CARRIERS.NAME.eq(name))
//                        .fetchAny()
//                        .into(Carrier.class)
//        );
    }

    public List<Carrier> findAllByName(String name) {

        var records = dsl.fetch(CARRIERS, CARRIERS.NAME.contains(name)).sortAsc(CARRIERS.ID);

        return records.map(
                        record -> Carrier.builder()
                                .id(record.getId())
                                .name(record.getName())
                                .phoneNumber(record.getPhoneNumber())
                                .build());
//
//        return dsl.selectFrom(CARRIERS)
//                .where(CARRIERS.NAME.contains(name))
//                .orderBy(CARRIERS.ID.sortAsc())
//                .fetch()
//                .map(c -> c.into(Carrier.class));
    }
}
