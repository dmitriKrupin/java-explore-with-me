package ru.practicum.ewm.exception;

public class NotFoundException extends RuntimeException {
    private final String parameter;

    public NotFoundException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
