package ru.ticketapp.carrier.mapper;

import ru.ticketapp.carrier.dto.CarrierDto;
import ru.ticketapp.carrier.model.Carrier;

public class CarrierMapper {

    public static Carrier toCarrier(CarrierDto carrierDto) {

        return Carrier.builder()
                .id(carrierDto.getId())
                .name(carrierDto.getName())
                .phoneNumber(carrierDto.getPhoneNumber())
                .build();
    }

    public static CarrierDto toCarrierDto(Carrier carrier) {

        return CarrierDto.builder()
                .id(carrier.getId())
                .name(carrier.getName())
                .phoneNumber(carrier.getPhoneNumber())
                .build();
    }
}
