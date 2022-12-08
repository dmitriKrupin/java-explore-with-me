package ru.practicum.ewm.exception;

public class InternalServerErrorException extends RuntimeException {
    private final String error;

    public InternalServerErrorException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
