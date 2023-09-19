package ru.ticketapp.route.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.ticketapp.route.mapper.RouteRecordMapper;
import ru.ticketapp.route.mapper.RouteRecordUnmapper;
import ru.ticketapp.route.model.Route;

import java.util.List;
import java.util.Optional;

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

//        return Optional.ofNullable(
//                dsl.insertInto(ROUTES)
//                        .set(routeRecordUnmapper.unmap(route))
//                        .returning()
//                        .fetchOptional()
//                        .orElseThrow(() -> new DataAccessException("Error inserting entity: " + route.getId()))
//                        .into(Route.class)
//        );
    }

    public Route get(Long id) {

        var records = Optional.ofNullable(
                dsl.fetchOne(ROUTES, ROUTES.ID.eq(id))
        );

        return routeRecordMapper.map(records.get());
    }

    public List<Route> getAllByPoint(String point) {

        var records = dsl.fetch(ROUTES,
                        ROUTES.DEPARTURE_POINT.contains(point).or(ROUTES.DESTINATION_POINT.contains(point)))
                        .sortAsc(ROUTES.ID);

        return records.map(routeRecordMapper::map);

//        return dsl.selectFrom(ROUTES)
//                .where(ROUTES.DEPARTURE_POINT.contains(point))
//                .or(ROUTES.DESTINATION_POINT.contains(point))
//                .orderBy(ROUTES.ID.sortAsc())
//                .fetch()
//                .map(m -> routeRecordMapper.map(m));
    }

    public List<Route> getAlByCarrierIdIn(List<Long> carrierIds) {

        var records = dsl.fetch(ROUTES, ROUTES.CARRIER_ID.in(carrierIds)).sortAsc(ROUTES.ID);

        return records.map(routeRecordMapper::map);

//        return dsl.selectFrom(ROUTES)
//                .where(ROUTES.CARRIER_ID.in(carrierIds))
//                .orderBy(ROUTES.ID.sortAsc())
//                .fetch()
//                .map(m -> routeRecordMapper.map(m));
    }
}
