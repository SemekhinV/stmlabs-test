package ru.ticketapp.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketDtoFromRequest {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Id of the user who bought the ticket (not specified when creating the ticket)")
    Long ownerId;

    @Schema(description = "Id of the route")
    Long routeId;

    @Schema(description = "Ticket departure time")
    LocalDateTime dateTime;

    @Schema(description = "Ticket seat number")
    Long seatNumber;

    @Schema(description = "Price of the ticket")
    Long price;

    @Schema(description = "Ticket status (purchased or not) is automatically set to FALSE when created")
    Boolean status;
}