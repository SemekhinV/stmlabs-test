package ru.ticketapp.route.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteDtoFromRequest {

    Long id;

    String departure;

    String destination;

    Long carrierId;

    Long duration;
}
