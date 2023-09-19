package ru.ticketapp.exception.validation;

public class PermissionDenied extends RuntimeException {

    public PermissionDenied(String message) {
        super(message);
    }
}
