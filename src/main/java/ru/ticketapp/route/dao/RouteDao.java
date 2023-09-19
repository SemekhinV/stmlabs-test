package ru.ticketapp.route.dao;

import ru.ticketapp.route.model.Route;

import java.util.List;
import java.util.Optional;

public interface RouteDao {

    Optional<Route> save(Route route);

    Optional<Route> get(Long id);

    Optional<Route> update(Route route);

    void delete(Long id);

    List<Route> findRoutesByPoint(String point);

    List<Route> findRoutesByCarrierId(List<Long> carriersId);
}
