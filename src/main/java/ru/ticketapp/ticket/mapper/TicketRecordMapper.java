package ru.ticketapp.ticket.mapper;

import lombok.RequiredArgsConstructor;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.ticketapp.jooq.tables.records.TicketsRecord;
import ru.ticketapp.route.model.Route;
import ru.ticketapp.route.repository.RouteRepository;
import ru.ticketapp.ticket.model.Ticket;
import ru.ticketapp.user.model.User;
import ru.ticketapp.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class TicketRecordMapper implements RecordMapper<TicketsRecord, Ticket> {

    private final RouteRepository routeRepository;

    private final UserRepository userRepository;

    @Override
    public Ticket map(TicketsRecord record) {

        if (record == null) {

            return null;
        }

        Ticket ticket = Ticket.builder()
                .id(record.getId())
                .owner(null)
                .route(null)
                .dateTime(record.getDateTime())
                .seatNumber(record.getSeatNumber())
                .price(record.getPrice())
                .status(record.getStatus())
                .build();

        Long userId = record.getOwnerId();

        User user = (userId == null) ? null : userRepository.get(userId);

        Route route = routeRepository.get(record.getRouteId());

        ticket.setOwner(user);

        ticket.setRoute(route);

        return ticket;
    }
}
