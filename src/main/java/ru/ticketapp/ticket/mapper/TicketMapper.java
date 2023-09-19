package ru.ticketapp.ticket.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.mapper.RouteMapper;
import ru.ticketapp.route.model.Route;
import ru.ticketapp.ticket.dto.TicketDtoFromRequest;
import ru.ticketapp.ticket.dto.TicketDtoToResponse;
import ru.ticketapp.ticket.model.Ticket;
import ru.ticketapp.user.mapper.UserMapper;
import ru.ticketapp.user.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {

    public static TicketDtoToResponse toTicketDto(Ticket ticket, RouteDtoToResponse route) {

        return TicketDtoToResponse
                .builder()
                .id(ticket.getId())
                .owner(ticket.getOwner() == null ? null : UserMapper.toUserDto(ticket.getOwner()))
                .route(route)
                .dateTime(ticket.getDateTime())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .build();
    }

    public static TicketDtoToResponse toTicketDto(Ticket ticket) {

        return TicketDtoToResponse
                .builder()
                .id(ticket.getId())
                .owner(ticket.getOwner() == null ? null : UserMapper.toUserDto(ticket.getOwner()))
                .route(ticket.getRoute() == null ? null : RouteMapper.toRouteDto(ticket.getRoute()))
                .dateTime(ticket.getDateTime())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .build();
    }

    public static Ticket toTicket(ResultSet tickets) throws SQLException {

        return Ticket
                .builder()
                .id(tickets.getLong("id"))
                .owner(tickets.getObject("owner_id", User.class))
                .route(tickets.getObject("route_id", Route.class))
                .dateTime(tickets.getObject("date_time", LocalDateTime.class))
                .seatNumber(tickets.getLong("set_number"))
                .price(tickets.getLong("price"))
                .status(tickets.getBoolean("status"))
                .build();
    }

    public static Ticket toTicket(TicketDtoToResponse ticketDtoToResponse){

        return Ticket
                .builder()
                .id(ticketDtoToResponse.getId())
                .owner(ticketDtoToResponse.getOwner() == null ? null : UserMapper.toUser(ticketDtoToResponse.getOwner()))
                .route(RouteMapper.toRoute(ticketDtoToResponse.getRoute()))
                .dateTime(ticketDtoToResponse.getDateTime())
                .seatNumber(ticketDtoToResponse.getSeatNumber())
                .price(ticketDtoToResponse.getPrice())
                .status(ticketDtoToResponse.getStatus())
                .build();
    }

    public static Ticket toTicket(TicketDtoFromRequest request){

        return Ticket
                .builder()
                .id(request.getId())
                .owner(request.getOwnerId() == null ?
                        null :
                        new User(request.getOwnerId(), "", "", "", null))
                .route(new Route(request.getRouteId(), "", "", null, null))
                .dateTime(request.getDateTime())
                .seatNumber(request.getSeatNumber())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();
    }

    public static Ticket toTicket(TicketDtoToResponse ticketDtoToResponse, RouteDtoToResponse routeDtoToResponse){

        return Ticket
                .builder()
                .id(ticketDtoToResponse.getId())
                .owner(ticketDtoToResponse.getOwner() == null ? null : UserMapper.toUser(ticketDtoToResponse.getOwner()))
                .route(RouteMapper.toRoute(routeDtoToResponse))
                .dateTime(ticketDtoToResponse.getDateTime())
                .seatNumber(ticketDtoToResponse.getSeatNumber())
                .price(ticketDtoToResponse.getPrice())
                .status(ticketDtoToResponse.getStatus())
                .build();
    }
}
