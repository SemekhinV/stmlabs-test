package ru.ticketapp.route.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.ticketapp.route.mapper.RouteRecordMapper;
import ru.ticketapp.route.mapper.RouteRecordUnmapper;
import ru.ticketapp.route.model.Route;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.ticketapp.jooq.tables.Routes.ROUTES;

@Repository
@RequiredArgsConstructor
public class RouteRepository {

    private final DSLContext dsl;

    private final RouteRecordMapper routeRecordMapper;

    private final RouteRecordUnmapper routeRecordUnmapper;

    public Long save(Route route) {

        return dsl.insertInto(ROUTES)
                .set(ROUTES.DEPARTURE_POINT, route.getDeparturePoint())
                .set(ROUTES.DESTINATION_POINT, route.getDestinationPoint())
                .set(ROUTES.CARRIER_ID, route.getCarrier().getId())
                .set(ROUTES.DURATION, route.getDuration())
                .returning(ROUTES.ID)
                .fetchOne()
                .getId();
    }

    public Route get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(ROUTES, ROUTES.ID.eq(id))
        );

        return routeRecordMapper.map(records.get());
    }

    public Integer update(Route route) {

        var records = dsl.fetchOne(ROUTES, ROUTES.ID.eq(route.getId()));

        if (records != null) {

            records.setDeparturePoint(
                    route.getDeparturePoint() == null ? records.getDeparturePoint() : route.getDeparturePoint());

            records.setDestinationPoint(
                    route.getDestinationPoint() == null ? records.getDestinationPoint() : route.getDestinationPoint());

            records.setCarrierId(
                    route.getCarrier() == null ? records.getCarrierId() : route.getCarrier().getId());

            records.setDuration(route.getDuration() == null ? records.getDuration() : route.getDuration());

            return records.store();
        }

        return 0;
    }

    public Integer delete(Long id) {

        var records = dsl.fetchOne(ROUTES, ROUTES.ID.eq(id));

        if (records != null) {

            return records.delete();
        }

        return 0;
    }

    public List<Route> getAllByPoint(String point) {

        var records = dsl.fetch(ROUTES,
                        ROUTES.DEPARTURE_POINT.contains(point).or(ROUTES.DESTINATION_POINT.contains(point)))
                        .sortAsc(ROUTES.ID);

        return records.map(routeRecordMapper::map);
    }

    public List<Route> getAlByCarrierIdIn(List<Long> carrierIds, PageRequest page) {

        var records = dsl.fetch(ROUTES,
                ROUTES.CARRIER_ID.in(carrierIds), ROUTES.ID.greaterOrEqual(page.getOffset())
        ).sortAsc(ROUTES.ID);

        return records.map(routeRecordMapper::map).stream().limit(page.getPageSize()).collect(Collectors.toList());
    }
}
