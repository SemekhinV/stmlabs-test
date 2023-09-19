package ru.ticketapp.route.service;

import org.springframework.data.domain.PageRequest;
import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;

import java.util.List;

public interface RouteService {

    RouteDtoFromRequest save(RouteDtoFromRequest routeDtoToResponse);

    RouteDtoToResponse get(Long id);

    String update(RouteDtoFromRequest routeDtoToResponse, Long userId);

    String delete(Long userId, Long routeId);

    List<RouteDtoToResponse> findRoutesByPoint(String search);

    List<RouteDtoToResponse> getAllByCarrierIdIn(List<Long> carrierIds, PageRequest page);
}
