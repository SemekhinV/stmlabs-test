package ru.ticketapp.ticket.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.ticketapp.ticket.mapper.TicketRecordMapper;
import ru.ticketapp.ticket.mapper.TicketRecordUnmapper;
import ru.ticketapp.ticket.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static ru.ticketapp.jooq.tables.Tickets.TICKETS;

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
    }

    public Ticket get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(TICKETS, TICKETS.ID.eq(id))
        );

        return ticketRecordMapper.map(records.get());
    }

    public List<Ticket> searchByDate(LocalDateTime date, PageRequest page) {

        var records = dsl.fetch(TICKETS,
                TICKETS.DATE_TIME.eq(date),
                TICKETS.ID.greaterOrEqual(page.getOffset())
        ).sortDesc(TICKETS.DATE_TIME);

        return records.map(ticketRecordMapper::map).stream().limit(page.getPageSize()).collect(Collectors.toList());
    }

    public List<Ticket> getAllByRouteIdIn(List<Long> routeIds) {

        var records = dsl.fetch(TICKETS, TICKETS.ID.in(routeIds)).sortAsc(TICKETS.DATE_TIME);

        return records.map(ticketRecordMapper::map);
    }

    public Integer buy(Long ownerId, Long ticketId) {

        var record = dsl.fetchOne(TICKETS, TICKETS.ID.eq(ticketId), TICKETS.STATUS.eq(FALSE));

        record.setOwnerId(ownerId);

        record.setStatus(TRUE);

        return record.store();
    }

    public List<Ticket> getAllByOwnerId(Long ownerId) {

        var records = dsl.fetch(TICKETS,
                TICKETS.OWNER_ID.eq(ownerId),
                TICKETS.STATUS.isTrue()
        ).sortAsc(TICKETS.DATE_TIME);

        return records.map(ticketRecordMapper::map);
    }
}
