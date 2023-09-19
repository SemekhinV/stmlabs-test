package ru.ticketapp.ticket.dto;

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

    Long id;

    Long ownerId;

    Long routeId;

    LocalDateTime dateTime;

    Long seatNumber;

    Long price;

    Boolean status;
}