package ru.ticketapp.route.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.service.RouteService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/routes")
public class RouteController {

    private final RouteService routeService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String ROUTE_ID_ERROR = "Передано пустое значение id вещи";

    private static final String USER_ID_ERROR = "Передано пустое значение id пользователя";

    @PostMapping()
    public RouteDtoFromRequest addRoute(
            @RequestBody RouteDtoFromRequest routeDtoToResponse,
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId
    ) {

        return routeService.save(routeDtoToResponse);
    }

    @GetMapping("/{routeId}")
    public RouteDtoToResponse getRoute(@PathVariable @NotNull(message = ROUTE_ID_ERROR) Long routeId) {

        return routeService.get(routeId);
    }
}
