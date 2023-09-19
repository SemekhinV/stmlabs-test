package ru.ticketapp.route.service;

import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;

import java.util.List;

public interface RouteService {

    RouteDtoFromRequest save(RouteDtoFromRequest routeDtoToResponse);

    RouteDtoToResponse get(Long id);

    RouteDtoToResponse update(RouteDtoFromRequest routeDtoToResponse);

    void delete(Long id);

    List<RouteDtoToResponse> findRoutesByPoint(String search);

    List<RouteDtoToResponse> getAllByCarrierIdIn(List<Long> carrierIds);
}
