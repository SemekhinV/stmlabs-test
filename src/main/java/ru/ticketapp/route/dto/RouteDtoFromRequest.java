package ru.ticketapp.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Departure point in the ticket route")
    String departure;

    @Schema(description = "Destination point in the ticket route")
    String destination;

    @Schema(description = "Id of carrier company")
    Long carrierId;

    @Schema(description = "Duration of the route")
    Long duration;
}
