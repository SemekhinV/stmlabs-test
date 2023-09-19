package ru.ticketapp.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ticket Controller", description = "Controller for creating, searching and purchasing new tickets")
public class TicketController {


    private final TicketServiceImpl ticketService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String TICKET_ID_ERROR = "Передано пустое значение id билета.";

    private static final String USER_ID_ERROR = "Передано пустое значение id покупателя.";

    @Operation(
            summary = "Create new ticket"
    )
    @PostMapping()
    public TicketDtoFromRequest addTicket(
            @RequestBody @Parameter(description = "Ticket data") TicketDtoFromRequest ticket
    ) {

        return ticketService.save(ticket);
    }

    @Operation(
            summary = "Get ticket",
            description = "Getting data of current ticket by id"
    )
    @GetMapping("/{ticketId}")
    public TicketDtoToResponse getTicket(@PathVariable @NotNull(message = TICKET_ID_ERROR) Long ticketId) {

        return ticketService.get(ticketId);
    }

    @Operation(
            summary = "Update data of ticket",
            description = "Updating data of ticket (only for ADMIN user group). Return status of operation or error."
    )
    @PatchMapping()
    public String updateTicket(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestBody @Parameter(description = "Ticket data to update") TicketDtoFromRequest ticket
    ) {

        return ticketService.update(ticket, userId);
    }

    @Operation(
            summary = "Delete ticket",
            description = "Delete ticket (only for ADMIN user group). Return status of operation or error."
    )
    @DeleteMapping("/{ticketId}")
    public String deleteTicket(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @PathVariable @NotNull(message = TICKET_ID_ERROR) Long ticketId
    ) {

        return ticketService.delete(ticketId, userId);
    }

    @Operation(
            summary = "Search by date",
            description = "Obtaining a list of tickets filtered by date"
    )
    @GetMapping("/search/date")
    public List<TicketDtoToResponse> searchTicketsByData(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) @Parameter(description = "Ticket create data for search") LocalDateTime date,
            @RequestParam(required = false) @Parameter(description = "Ticket id to get from") Integer from,
            @RequestParam(required = false) @Parameter(description = "Size of page of tickets") Integer size
    ) {

        return ticketService.getAllTicketsByDate(
                userId, date, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @Operation(
            summary = "Search by point",
            description = "Obtaining a list of tickets filtered by point departure/destination"
    )
    @GetMapping("/search/point")
    public List<TicketDtoToResponse> searchTicketsByPoint(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) @Parameter(description = "Ticket departure/destination point for search") String points,
            @RequestParam(required = false) @Parameter(description = "Ticket id to get from") Integer from,
            @RequestParam(required = false) @Parameter(description = "Size of page of tickets") Integer size
    ) {

        return ticketService.getAllTicketsByPoint(
                userId, points, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @Operation(
            summary = "Search by carrier name",
            description = "Obtaining a list of tickets filtered by carrier name"
    )
    @GetMapping("/search/carrier")
    public List<TicketDtoToResponse> searchTicketsByCarrierName(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestParam(required = false) @Parameter(description = "Ticket carrier name for search") String carrierName,
            @RequestParam(required = false) @Parameter(description = "Ticket id to get from") Integer from,
            @RequestParam(required = false) @Parameter(description = "Size of page of tickets") Integer size
    ) {

        return ticketService.getAllTicketsByCarrierName(
                userId, carrierName, PageableImpl.of(from, size, Sort.by("dateTime").descending())
        );
    }

    @Operation(
            summary = "Get all user tickets",
            description = "Obtaining a list of tickets filtered by owner id and purchase status TRUE"
    )
    @GetMapping("/search/owner")
    public List<TicketDtoToResponse> getAllTicketByOwner(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long ownerId
    ) {

        return ticketService.getAllByOwnerId(ownerId);
    }

    @Operation(
            summary = "Buy ticket",
            description = "Purchase of ticket with id = itemId by user with id = userId " +
                    "(user must be registered in the system for successful purchase)"
    )
    @GetMapping("/buy/{ticketId}")
    public String buyTicket(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @PathVariable @NotNull(message = TICKET_ID_ERROR) Long ticketId
    ) {

        return ticketService.buy(userId, ticketId);
    }
}
