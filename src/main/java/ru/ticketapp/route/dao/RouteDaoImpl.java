package ru.ticketapp.route.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ticketapp.route.mapper.RouteMapper;
import ru.ticketapp.route.model.Route;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RouteDaoImpl implements RouteDao{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Route> save(Route route) {

        jdbcTemplate.update(
                "INSERT INTO routes (id, departure_point, destination_point, carrier_id, duration) " +
                        "VALUES (?,?,?,?,?, ?)",
                route.getId(),
                route.getDeparturePoint(),
                route.getDestinationPoint(),
                route.getCarrier(),
                route.getDuration());

        return get(route.getId());
    }

    @Override
    public Optional<Route> get(Long id) {

        SqlRowSet routeRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM routes " +
                        "WHERE id = ?", id);

        if (routeRow.next()) {

            Route route = Route
                    .builder()
                    .id(routeRow.getLong("id"))
                    .departurePoint(routeRow.getString("departure_point"))
                    .destinationPoint(routeRow.getString("destination_point"))
//                    .carrierId(routeRow.getLong("carrier_id"))
                    .duration(routeRow.getLong("duration"))
                    .build();

            return Optional.of(route);
        } else {

            return Optional.empty();
        }
    }

    @Override
    public Optional<Route> update(Route route) {

        jdbcTemplate.update(
                "UPDATE routes " +
                        "SET id= ?, " +
                        "departure_point = ?, " +
                        "destination_point = ?, " +
                        "carrier_id = ?, " +
                        "duration = ? " +
                        "WHERE id = " + route.getId(),
                route.getId(),
                route.getDeparturePoint(),
                route.getDestinationPoint(),
//                route.getCarrierId(),
                route.getDuration());

        return get(route.getId());
    }

    @Override
    public void delete(Long id) {

        jdbcTemplate.update("DELETE FROM Route r WHERE r.id = ?", id);
    }

    @Override
    public List<Route> findRoutesByPoint(String point) {

        return jdbcTemplate.query(
                "SELECT route FROM Route route " +
                        "WHERE (UPPER(route.departure_point) LIKE UPPER(CONCAT('%', ?1, '%')) " +
                        "OR (UPPER(route.destination_point) LIKE UPPER(CONCAT('%', ?1, '%'))",
                (rs, rowNum) -> RouteMapper.toRoute(rs), point);

    }

    @Override
    public List<Route> findRoutesByCarrierId(List<Long> carrierId) {

        return jdbcTemplate.query(
                "SELECT route FROM Route route " +
                        "WHERE route.carrierId in :carrierId",
                (rs, rowNum) -> RouteMapper.toRoute(rs), carrierId
        );
    }
}
