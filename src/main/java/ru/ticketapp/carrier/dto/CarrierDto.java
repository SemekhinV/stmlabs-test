package ru.ticketapp.carrier.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarrierDto {

    Long id;

    @NotNull(message = "Название компании перевозчика не может быть пустым.")
    String name;

    @NotNull(message = "Необходимо указать телефонный номер компании перевозчика.")
    String phoneNumber;
}
