package ru.ticketapp.ticket.service;

import org.springframework.data.domain.PageRequest;
import ru.ticketapp.ticket.dto.TicketDtoFromRequest;
import ru.ticketapp.ticket.dto.TicketDtoToResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    TicketDtoFromRequest save(TicketDtoFromRequest ticketDtoFromRequest);

    TicketDtoToResponse get(Long id);

    String delete(Long ticketId, Long userId);

    String update(TicketDtoFromRequest request, Long userId);

    List<TicketDtoToResponse> getAllTicketsByDate(Long ownerId, LocalDateTime dateToSearch, PageRequest page);

    List<TicketDtoToResponse> getAllTicketsByPoint(Long ownerId, String points, PageRequest page);

    List<TicketDtoToResponse> getAllTicketsByCarrierName(Long ownerId, String carrierName, PageRequest page);

    String buy(Long ownerId, Long ticketId);

    List<TicketDtoToResponse> getAllByOwnerId(Long ownerId);
}
