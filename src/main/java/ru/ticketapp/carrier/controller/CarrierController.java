package ru.ticketapp.carrier.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.service.CarrierService;
import ru.ticketapp.ticket.dto.TicketDtoFromRequest;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/carriers")
@Tag(name = "Carrier controller", description = "controller for creating new carriers (created for testing the application)")
public class CarrierController {

    private final CarrierService carrierService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String CARRIER_ID_ERROR = "Передано пустое значение id вещи";

    private static final String USER_ID_ERROR = "Передано пустое значение id пользователя";

    @Operation(
            summary = "Create new carrier"
    )
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public CarrierDto addCarrier(
            @RequestBody @Parameter(description = "Carrier data") CarrierDto carrierDto,
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId
    ) {

        return carrierService.save(carrierDto);
    }

    @Operation(
            summary = "Get carrier",
            description = "Getting data of current carrier by id"
    )
    @GetMapping("/{carrierId}")
    public CarrierDto getCarrier(@PathVariable @NotNull(message = CARRIER_ID_ERROR) Long carrierId) {

        return carrierService.get(carrierId);
    }

    @Operation(
            summary = "Update data of carrier",
            description = "Updating data of carrier (only for ADMIN user group). Return status of operation or error."
    )
    @PatchMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateCarrier(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestBody @Parameter(description = "Carrier data to update") CarrierDto carrierDto
    ) {

        return carrierService.update(carrierDto, userId);
    }

    @Operation(
            summary = "Delete carrier",
            description = "Delete carrier (only for ADMIN user group). Return status of operation or error."
    )
    @DeleteMapping("/{carrierId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCarrier(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @PathVariable @NotNull(message = CARRIER_ID_ERROR) Long carrierId
    ) {

        return carrierService.delete(carrierId, userId);
    }
}
