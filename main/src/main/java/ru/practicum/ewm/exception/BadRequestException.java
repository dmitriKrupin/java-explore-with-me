package ru.practicum.ewm.exception;

public class BadRequestException extends RuntimeException {
    private final String parameter;

    public BadRequestException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
