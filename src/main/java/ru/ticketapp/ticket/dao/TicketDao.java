package ru.ticketapp.ticket.dao;

import org.springframework.data.domain.PageRequest;
import ru.ticketapp.ticket.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketDao {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> update(Ticket ticket);

    Optional<Ticket> get(Long id);

    void delete(Long id);

    List<Ticket> searchByDate(LocalDateTime date, PageRequest page);

    List<Ticket> getById(List<Long> ticketIds);

    Integer buy(Long ownerId, Long ticketId);
}
