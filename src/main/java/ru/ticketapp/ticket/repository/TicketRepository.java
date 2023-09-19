package ru.ticketapp.ticket.repository;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static ru.ticketapp.jooq.tables.Tickets.TICKETS;

import ru.ticketapp.jooq.tables.records.TicketsRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.ticketapp.ticket.mapper.TicketRecordMapper;
import ru.ticketapp.ticket.mapper.TicketRecordUnmapper;
import ru.ticketapp.ticket.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepository {

    private final DSLContext dsl;

    private final TicketRecordMapper ticketRecordMapper;

    private final TicketRecordUnmapper ticketRecordUnmapper;

    public Long save(Ticket ticket) {

        return dsl.insertInto(TICKETS)
                .set(TICKETS.ROUTE_ID, ticket.getRoute().getId())
                .set(TICKETS.DATE_TIME, ticket.getDateTime())
                .set(TICKETS.SEAT_NUMBER, ticket.getSeatNumber())
                .set(TICKETS.STATUS, ticket.getStatus())
                .set(TICKETS.PRICE, ticket.getPrice())
                .returning(TICKETS.ID)
                .fetchOne()
                .getId();

//        return Optional.ofNullable(
//                dsl.insertInto(TICKETS)
//                        .set(ticketRecordUnmapper.unmap(ticket))
//                        .returning()
//                        .fetchOptional()
//                        .orElseThrow(() -> new DataAccessException("Error inserting entity: " + ticket.getId()))
//                        .into(Ticket.class));
    }

    public Ticket get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(TICKETS, TICKETS.ID.eq(id))
        );

        return ticketRecordMapper.map(records.get());
//
//        return Optional.of(dsl.selectFrom(TICKETS)
//                .where(TICKETS.ID.eq(id))
//                .fetchAny()
//                .map(t -> ticketRecordMapper.map((TicketsRecord) t)));
    }

    public List<Ticket> searchByDate(LocalDateTime date, PageRequest page) {

        var records = dsl.fetch(TICKETS, TICKETS.DATE_TIME.eq(date)).sortDesc(TICKETS.DATE_TIME);

        return records.map(ticketRecordMapper::map);

//        return dsl.selectFrom(TICKETS)
//                .where(TICKETS.DATE_TIME.eq(date))
//                .orderBy(TICKETS.DATE_TIME.sortDesc())
//                .offset(page.getOffset())
//                .limit(page.getPageSize())
//                .fetch()
//                .map(t -> ticketRecordMapper.map(t));
    }

    public List<Ticket> getAllByRouteIdIn(List<Long> routeIds) {

        var records = dsl.fetch(TICKETS, TICKETS.ID.in(routeIds)).sortAsc(TICKETS.ID);

        return records.map(ticketRecordMapper::map);

//        return dsl.selectFrom(TICKETS)
//                .where(TICKETS.ROUTE_ID.in(routeIds))
//                .orderBy(TICKETS.DATE_TIME.sortDesc())
//                .fetch()
//                .map(t -> ticketRecordMapper.map(t));
    }

    public Integer buy(Long ownerId, Long ticketId) {

        var record = dsl.fetchOne(TICKETS, TICKETS.ID.eq(ticketId), TICKETS.STATUS.eq(FALSE));

        record.setOwnerId(ownerId);

        record.setStatus(TRUE);

        return record.store();

//        return Optional.ofNullable(
//                dsl.update(TICKETS)
//                        .set(TICKETS.STATUS, TRUE)
//                        .set(TICKETS.OWNER_ID, ownerId)
//                        .where(TICKETS.ID.eq(ticketId), TICKETS.STATUS.eq(FALSE))
//                        .returning()
//                        .fetchOptional()
//                        .orElseThrow(() -> new DataAccessException("Error updating entity: " + ticketId))
//                        .into(Ticket.class));
    }
}
