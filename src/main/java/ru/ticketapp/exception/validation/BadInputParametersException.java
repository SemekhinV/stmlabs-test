package ru.ticketapp.exception.validation;

public class BadInputParametersException extends RuntimeException{

    public BadInputParametersException(String message) {
        super(message);
    }
}
