package ru.ticketapp.ticket.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ticketapp.route.model.Route;
import ru.ticketapp.ticket.mapper.TicketMapper;
import ru.ticketapp.ticket.model.Ticket;
import ru.ticketapp.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketDaoImpl implements TicketDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Ticket> save(Ticket ticket) {

         jdbcTemplate.update(
                "INSERT INTO tickets (id, owner_id, route_id, date_time, seat_number, price) VALUES (?,?,?,?,?,?,?)",
                ticket.getId(),
                ticket.getOwner(),
                ticket.getRoute(),
                ticket.getDateTime(),
                ticket.getSeatNumber(),
                ticket.getPrice());

         return get(ticket.getId());
    }

    @Override
    public Optional<Ticket> get(Long id) {

        SqlRowSet ticketRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM tickets " +
                        "WHERE id = ?", id);

        if (ticketRow.next()) {

            Ticket ticket = Ticket.builder()
                    .id(ticketRow.getLong("id"))
                    .owner(ticketRow.getObject("owner_id", User.class))
                    .route(ticketRow.getObject("route_id", Route.class))
                    .dateTime(ticketRow.getObject("date_time", LocalDateTime.class))
                    .seatNumber(ticketRow.getLong("seat_number"))
                    .price(ticketRow.getLong("price"))
                    .build();

            return Optional.of(ticket);
        } else {

            return Optional.empty();
        }
    }
    @Override
    public Optional<Ticket> update(Ticket ticket) {

        jdbcTemplate.update(
                "UPDATE tickets " +
                        "SET owner_id = ?, " +
                        "route_id = ?, " +
                        "date_time = ?, " +
                        "seat_number = ?, " +
                        "price = ? " +
                        "WHERE id = " + ticket.getId(),
                ticket.getOwner(),
                ticket.getRoute(),
                ticket.getDateTime(),
                ticket.getSeatNumber(),
                ticket.getPrice());

        return get(ticket.getId());
    }

    @Override
    public void delete(Long id) {

        jdbcTemplate.update("DELETE FROM Ticket t WHERE t.id = ?", id);
    }

    @Override
    public List<Ticket> searchByDate(LocalDateTime date, PageRequest page) {

        return jdbcTemplate.query(
                "SELECT t FROM Ticket t " +
                        "WHERE t.dateTime = ? " +
                        "ORDER BY t.dateTime DESC " +
                        "LIMIT " + page.getPageSize() + " " + "OFFSET " + page.getOffset(),
                (rs, rowNum) -> TicketMapper.toTicket(rs), date);
    }

    @Override
    public List<Ticket> getById(List<Long> ticketIds) {

        return jdbcTemplate.query(
                "SELECT t FROM Ticket t WHERE t.id = ? ORDER BY t.id ASC",
                (rs, rowNum) -> TicketMapper.toTicket(rs), ticketIds);
    }

    @Override
    public Integer buy(Long ownerId, Long ticketId) {

        return jdbcTemplate.update(
                "UPDATE tickets " +
                        "SET status TRUE, " +
                        "owner_id = ? " +
                        "WHERE id = " + ticketId,
                ownerId
        );
    }
}
