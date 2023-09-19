package ru.ticketapp.exception.validation;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}

