package ru.ticketapp.route.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.ticketapp.carrier.dto.CarrierDto;


@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteDtoToResponse {

    Long id;

    String departurePoint;

    String destinationPoint;

    CarrierDto carrier;

    Long duration;
}
