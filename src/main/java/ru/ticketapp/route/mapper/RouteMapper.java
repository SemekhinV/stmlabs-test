package ru.ticketapp.route.mapper;

import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.mapper.CarrierMapper;
import ru.ticketapp.carrier.model.Carrier;
import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.model.Route;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteMapper {

    public static Route toRoute(RouteDtoToResponse routeDtoToResponse) {

        return Route.builder()
                .id(routeDtoToResponse.getId())
                .departurePoint(routeDtoToResponse.getDeparturePoint())
                .destinationPoint(routeDtoToResponse.getDestinationPoint())
                .carrier(CarrierMapper.toCarrier(routeDtoToResponse.getCarrier()))
                .duration(routeDtoToResponse.getDuration())
                .build();
    }

    public static Route toRoute(RouteDtoFromRequest request) {

        return Route.builder()
                .id(request.getId())
                .departurePoint(request.getDeparture())
                .destinationPoint(request.getDestination())
                .carrier(new Carrier(request.getCarrierId(), "", ""))
                .duration(request.getDuration())
                .build();
    }

    public static Route toRoute(ResultSet route) throws SQLException {

        return Route.builder()
                .id(route.getLong("id"))
                .departurePoint(route.getString("departure_point"))
                .destinationPoint(route.getString("destination_point"))
                .carrier(route.getObject("carrier_id", Carrier.class))
                .duration(route.getLong("duration"))
                .build();
    }

    public static RouteDtoToResponse toRouteDto(Route route, CarrierDto carrier) {

        return RouteDtoToResponse.builder()
                .id(route.getId())
                .departurePoint(route.getDeparturePoint())
                .destinationPoint(route.getDestinationPoint())
                .carrier(carrier)
                .duration(route.getDuration())
                .build();
    }

    public static RouteDtoToResponse toRouteDto(Route route) {

        return RouteDtoToResponse.builder()
                .id(route.getId())
                .departurePoint(route.getDeparturePoint())
                .destinationPoint(route.getDestinationPoint())
                .carrier(CarrierMapper.toCarrierDto(route.getCarrier()))
                .duration(route.getDuration())
                .build();
    }
}
