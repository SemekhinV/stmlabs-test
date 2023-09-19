package ru.ticketapp.ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.ticket.dto.TicketDtoFromRequest;
import ru.ticketapp.ticket.dto.TicketDtoToResponse;
import ru.ticketapp.ticket.service.TicketServiceImpl;
import ru.ticketapp.tools.PageableImpl;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tickets")
public class TicketController {


    private final TicketServiceImpl ticketService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String TICKET_ID_ERROR = "Передано пустое значение id билета.";

    private static final String USER_ID_ERROR = "Передано пустое значение id покупателя.";

    @PostMapping()
    public TicketDtoFromRequest addTicket(@RequestBody TicketDtoFromRequest ticket) {

        return ticketService.save(ticket);
    }

    @GetMapping("/{ticketId}")
    public TicketDtoToResponse getTicket(@PathVariable @NotNull(message = TICKET_ID_ERROR) Long ticketId) {

        return ticketService.get(ticketId);
    }

    @GetMapping("/search/date")
    public List<TicketDtoToResponse> searchTicketsByData(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) LocalDateTime date,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size
    ) {

        return ticketService.getAllTicketsByDate(
                userId, date, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @GetMapping("/search/point")
    public List<TicketDtoToResponse> searchTicketsByPoint(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) String points,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size
    ) {

        return ticketService.getAllTicketsByPoint(
                userId, points, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @GetMapping("/search/carrier")
    public List<TicketDtoToResponse> searchTicketsByCarrierName(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) String carrierName,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size
    ) {

        return ticketService.getAllTicketsByCarrierName(
                userId, carrierName, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @GetMapping("/search/owner")
    public List<TicketDtoToResponse> getAllTicketByOwner(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long ownerId
    ) {

        return ticketService.getAllByOwnerId(ownerId);
    }
}
