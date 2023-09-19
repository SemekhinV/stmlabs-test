package ru.ticketapp.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketDtoToResponse {

    Long id;

    UserDto owner;

    RouteDtoToResponse route;

    LocalDateTime dateTime;

    Long seatNumber;

    Long price;

    Boolean status;
}
