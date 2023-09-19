package ru.ticketapp.carrier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.service.CarrierService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String CARRIER_ID_ERROR = "Передано пустое значение id вещи";

    private static final String USER_ID_ERROR = "Передано пустое значение id пользователя";

    @PostMapping()
    public CarrierDto addCarrier(
            @RequestBody CarrierDto carrierDto,
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId
    ) {

        return carrierService.save(carrierDto);
    }

    @GetMapping("/{carrierId}")
    public CarrierDto getCarrier(@PathVariable @NotNull(message = CARRIER_ID_ERROR) Long carrierId) {

        return carrierService.get(carrierId);
    }
}
