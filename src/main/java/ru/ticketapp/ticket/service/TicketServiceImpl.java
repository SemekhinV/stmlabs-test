package ru.ticketapp.ticket.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.service.CarrierService;
import ru.ticketapp.exception.validation.BadInputParametersException;
import ru.ticketapp.exception.validation.EntityNotFoundException;
import ru.ticketapp.exception.validation.InvalidValueException;
import ru.ticketapp.route.dto.RouteDtoToResponse;
import ru.ticketapp.route.service.RouteService;
import ru.ticketapp.ticket.dto.TicketDtoFromRequest;
import ru.ticketapp.ticket.dto.TicketDtoToResponse;
import ru.ticketapp.ticket.mapper.TicketMapper;
import ru.ticketapp.ticket.model.Ticket;
import ru.ticketapp.ticket.repository.TicketRepository;
import ru.ticketapp.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final RouteService routeService;

    private final CarrierService carrierService;

    private final UserService userService;

    private void isValid(TicketDtoFromRequest dto) {

        if (dto.getDateTime() == null
                || dto.getSeatNumber() == null
                || dto.getPrice() == null
                || dto.getRouteId() == null
        ) {
            throw new InvalidValueException("Ошибка создания билета, передан один или несколько пустых параметров.");
        }

        if (dto.getDateTime().isBefore(LocalDateTime.now()) || dto.getDateTime().equals(LocalDateTime.now())) {

            throw new InvalidValueException("Время отправления в билете не может быть в прошлом.");
        }

        if (dto.getPrice() <= 0) {
            throw new InvalidValueException("Стоимость билета должна быть больше нуля.");
        }

        if (dto.getSeatNumber() <= 0) {
            throw new InvalidValueException("Номер посадочного места не может быть отрицательным.");
        }
    }

    @Override
    public TicketDtoFromRequest save(TicketDtoFromRequest request) {

        isValid(request);

        request.setStatus(false);

        request.setId(ticketRepository.save(TicketMapper.toTicket(request)));

        return request;
    }

    @Override
    public TicketDtoToResponse get(Long id) {

        Ticket ticket = ticketRepository.get(id);

        if (ticket == null) {

            throw new EntityNotFoundException("Выбранный вами билет не найден");
        }

        return TicketMapper.toTicketDto(ticket);
    }

    @Override
    public List<TicketDtoToResponse> getAllTicketsByDate(Long ownerId, LocalDateTime dateToSearch, PageRequest page) {

        if (dateToSearch != null && dateToSearch.isBefore(LocalDateTime.now())) {
            throw new InvalidValueException("Время отправления в билете не может быть в прошлом или отсутствовать.");
        }

        List<TicketDtoToResponse> tickets;

        try {

            tickets = ticketRepository.searchByDate(dateToSearch, page)
                    .stream()
                    .map(TicketMapper::toTicketDto)
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {

            throw new BadInputParametersException("Неверные аргументы поиска билетов по дате.");
        }

        tickets = tickets.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return tickets.isEmpty() ? List.of() : tickets;
    }

    @Override
    public List<TicketDtoToResponse> getAllTicketsByPoint(Long ownerId, String points, PageRequest page) {


        if (points == null || "".equals(points)) {

            throw new InvalidValueException("Переданы пустые данные поиска.");
        }

        List<TicketDtoToResponse> tickets;

        try {

            tickets = ticketRepository.getAllByRouteIdIn(
                    routeService.findRoutesByPoint(points)
                            .stream()
                            .map(RouteDtoToResponse::getId)
                            .collect(Collectors.toList()))
                    .stream()
                    .map(TicketMapper::toTicketDto)
                    .collect(Collectors.toList()
            );
        } catch (DataAccessException e) {

            throw new BadInputParametersException("Неверные аргументы поиска билетов по пути.");
        }

        tickets = tickets.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return tickets.isEmpty() ? List.of() : tickets;
    }

    @Override
    public List<TicketDtoToResponse> getAllTicketsByCarrierName(Long ownerId, String carrierName, PageRequest page) {

        if (carrierName == null || "".equals(carrierName)) {

            throw new InvalidValueException("Переданы пустые данные поиска.");
        }

        List<TicketDtoToResponse> tickets;

        try {

            tickets = ticketRepository.getAllByRouteIdIn(
                    routeService.getAllByCarrierIdIn(
                            carrierService.getAllByName(carrierName)
                                    .stream()
                                    .map(CarrierDto::getId)
                                    .collect(Collectors.toList()))
                            .stream()
                            .map(RouteDtoToResponse::getId)
                            .collect(Collectors.toList()))
                    .stream()
                    .map(TicketMapper::toTicketDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {

            throw new BadInputParametersException("Неверные аргументы поиска билетов по имени перевозчика.");
        }

        tickets = tickets.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return tickets.isEmpty() ? List.of() : tickets;
    }

    @Override
    public String buy(Long ownerId, Long ticketId) {

        userService.getUser(ownerId);

        int status = ticketRepository.buy(ownerId, ticketId);

        if (status != 2) {
            throw new InvalidValueException("Ошибка покупки вещи");
        }

        return "Вещь успешно куплена.";
    }
}
