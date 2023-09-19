package ru.ticketapp.route.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.service.RouteService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/routes")
@Tag(name = "Route controller", description = "controller for creating new routes (created for testing the application)")
public class RouteController {

    private final RouteService routeService;

    private static final String USER_ID = "X-Sharer-User-Id";

    private static final String ROUTE_ID_ERROR = "Передано пустое значение id вещи";

    private static final String USER_ID_ERROR = "Передано пустое значение id пользователя";

    @Operation(
            summary = "Create new route"
    )
    @PostMapping()
    public RouteDtoFromRequest addRoute(
            @RequestBody @Parameter(description = "Route data") RouteDtoFromRequest routeDtoToResponse,
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId
    ) {

        return routeService.save(routeDtoToResponse);
    }

    @Operation(
            summary = "Get route",
            description = "Getting data of current route by id"
    )
    @GetMapping("/{routeId}")
    public RouteDtoToResponse getRoute(@PathVariable @NotNull(message = ROUTE_ID_ERROR) Long routeId) {

        return routeService.get(routeId);
    }

    @Operation(
            summary = "Update data of route",
            description = "Updating data of route (only for ADMIN user group). Return status of operation or error."
    )
    @PatchMapping()
    public String updateRoute(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @RequestBody @Parameter(description = "Route data to update") RouteDtoFromRequest route
    ) {

        return routeService.update(route, userId);
    }

    @Operation(
            summary = "Delete route",
            description = "Delete route (only for ADMIN user group). Return status of operation or error."
    )
    @DeleteMapping("/{routeId}")
    public String deleteRoute(
            @RequestHeader(value = USER_ID, required = false) @NotNull(message = USER_ID_ERROR) Long userId,
            @PathVariable @NotNull(message = ROUTE_ID_ERROR) Long routeId
    ) {

        return routeService.delete(routeId, userId);
    }
}
