package ru.ticketapp.ticket.mapper;

import lombok.RequiredArgsConstructor;
import org.hibernate.MappingException;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.springframework.stereotype.Component;
import ru.ticketapp.jooq.tables.records.TicketsRecord;
import ru.ticketapp.ticket.model.Ticket;

import static ru.ticketapp.jooq.tables.Tickets.TICKETS;

@Component
@RequiredArgsConstructor
public class TicketRecordUnmapper implements RecordUnmapper<Ticket, TicketsRecord> {

    private final DSLContext dsl;

    @Override
    public TicketsRecord unmap(Ticket ticket) throws MappingException {

        TicketsRecord record = dsl.newRecord(TICKETS, ticket);

        record.setDateTime(ticket.getDateTime());

        record.setOwnerId(null);

        record.setRouteId(ticket.getRoute().getId());

        return record;
    }
}
