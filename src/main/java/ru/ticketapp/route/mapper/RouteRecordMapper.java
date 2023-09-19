package ru.ticketapp.route.mapper;

import lombok.RequiredArgsConstructor;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.ticketapp.carrier.model.Carrier;
import ru.ticketapp.carrier.repository.CarrierRepository;
import ru.ticketapp.jooq.tables.records.RoutesRecord;
import ru.ticketapp.route.model.Route;

@Component
@RequiredArgsConstructor
public class RouteRecordMapper implements RecordMapper<RoutesRecord, Route> {

    private final CarrierRepository carrierRepository;

    @Override
    public Route map(RoutesRecord record) {

        if (record == null) {
            return null;
        }

        Carrier carrier = carrierRepository.get(record.getCarrierId());

        return Route.builder()
                .id(record.getId())
                .departurePoint(record.getDeparturePoint())
                .destinationPoint(record.getDestinationPoint())
                .carrier(carrier)
                .duration(record.getDuration())
                .build();
    }
}
