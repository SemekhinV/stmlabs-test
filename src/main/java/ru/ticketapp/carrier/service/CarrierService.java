package ru.ticketapp.carrier.service;

import org.springframework.data.domain.PageRequest;
import ru.ticketapp.carrier.dto.CarrierDto;

import java.util.List;

public interface CarrierService {

    CarrierDto save(CarrierDto carrierDto);

    CarrierDto get(Long id);

    CarrierDto get(String name);

    String update(CarrierDto carrier, Long userId);

    String delete(Long userId, Long carrierId);

    List<CarrierDto> getAllByName(String name, PageRequest page);
}
