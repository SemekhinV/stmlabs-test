package ru.ticketapp.carrier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.mapper.CarrierMapper;
import ru.ticketapp.carrier.model.Carrier;
import ru.ticketapp.carrier.repository.CarrierRepository;
import ru.ticketapp.exception.validation.EntityNotFoundException;
import ru.ticketapp.exception.validation.InvalidValueException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;

    @Override
    public CarrierDto save(CarrierDto carrierDto) {


        carrierDto.setId(carrierRepository.save(CarrierMapper.toCarrier(carrierDto)));

        if (carrierDto.getId() == null) {
            throw new InvalidValueException("Ошибка создания нового поставщика");
        }

        return carrierDto;

    }

    @Override
    public CarrierDto get(Long id) {

        Carrier carrier = carrierRepository.get(id);

        if (carrier == null) {
            throw new EntityNotFoundException("Ошибка запроса поставщика.");
        }

        return CarrierMapper.toCarrierDto(carrier);
    }

    @Override
    public CarrierDto get(String name) {

        Carrier carrier = carrierRepository.get(name);

        if (carrier == null) {
            throw new EntityNotFoundException("Ошибка запроса поставщика.");
        }

        return CarrierMapper.toCarrierDto(carrier);
    }

    @Override
    public List<CarrierDto> getAllByName(String name, PageRequest page) {

        List<CarrierDto> carriers;

        try {

            carriers = carrierRepository.findAllByName(name, page)
                    .stream()
                    .map(CarrierMapper::toCarrierDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Ошибка поиска поставщика.");
        }

        return carriers.isEmpty() ? List.of() : carriers;
    }
}
