package ru.ticketapp.route.mapper;

import ru.ticketapp.jooq.tables.records.RoutesRecord;
import lombok.RequiredArgsConstructor;
import org.hibernate.MappingException;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.springframework.stereotype.Component;
import ru.ticketapp.route.model.Route;

import static ru.ticketapp.jooq.tables.Routes.ROUTES;

@Component
@RequiredArgsConstructor
public class RouteRecordUnmapper implements RecordUnmapper<Route, RoutesRecord> {

    private final DSLContext dsl;

    @Override
    public RoutesRecord unmap(Route route) throws MappingException {

        RoutesRecord record = dsl.newRecord(ROUTES, route);

        record.setCarrierId(route.getCarrier().getId());

        return record;
    }
}
