package ru.ticketapp.route.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ticketapp.carrier.service.CarrierService;
import ru.ticketapp.exception.validation.BadInputParametersException;
import ru.ticketapp.exception.validation.EntityNotFoundException;
import ru.ticketapp.exception.validation.InvalidValueException;
import ru.ticketapp.route.dto.RouteDtoFromRequest;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.mapper.RouteMapper;
import ru.ticketapp.route.model.Route;
import ru.ticketapp.route.repository.RouteRepository;
import ru.ticketapp.user.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final UserService userService;

    private final CarrierService carrierService;

    private void isValid(RouteDtoFromRequest dto) {

        if (dto.getDeparture() == null
                || dto.getDestination() == null
                || dto.getCarrierId() == null
                || dto.getDuration() == null
        ) {

            throw new InvalidValueException("Ошибка создания пути, передан один или несколько пустых параметров.");
        }

        if ("".equals(dto.getDeparture()) || "".equals(dto.getDestination())) {

            throw new InvalidValueException("Ошибка создания пути, пункт назначения/отправления не может быть пустым.");
        }
    }

    @Override
    public RouteDtoFromRequest save(RouteDtoFromRequest request) {

        isValid(request);

        request.setId(routeRepository.save(RouteMapper.toRoute(request)));

        return request;
    }

    @Override
    public RouteDtoToResponse get(Long id) {

        Route route = routeRepository.get(id);

        if (route == null) {
            throw new EntityNotFoundException("Выбранный вами маршрут не найден.");
        }

        return RouteMapper.toRouteDto(route);
    }

    @Override
    public String update(RouteDtoFromRequest route, Long userId) {

        userService.checkRole(userId);

        int answer = routeRepository.update(RouteMapper.toRoute(route));

        return answer == 1 ? "Изменение данных маршрута успешно произведено" : "Ошибка изменения данных маршрута, " +
                "сущность не найдена";
    }

    @Override
    public String delete(Long userId, Long routeId) {

        userService.checkRole(userId);

        int answer = routeRepository.delete(routeId);

        return answer == 1 ? "Удаление маршрута произведено успешно" : "Ошибка удаления маршрута, " +
                "сущность не найдена";
    }

    @Override
    public List<RouteDtoToResponse> findRoutesByPoint(String search) {

        List<RouteDtoToResponse> routes;

        try {

            routes = routeRepository
                    .getAllByPoint(search)
                    .stream()
                    .map(RouteMapper::toRouteDto)
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {

            throw new BadInputParametersException("Неверные аргументы поиска маршрутов.");
        }

        routes = routes.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return routes.isEmpty() ? List.of() : routes;
    }

    @Override
    public List<RouteDtoToResponse> getAllByCarrierIdIn(List<Long> carrierIds, PageRequest page) {

        return routeRepository.getAlByCarrierIdIn(carrierIds, page)
                .stream()
                .filter(Objects::nonNull)
                .map(RouteMapper::toRouteDto)
                .collect(Collectors.toList());
    }
}
