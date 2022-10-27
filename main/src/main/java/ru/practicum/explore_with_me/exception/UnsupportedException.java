package ru.practicum.explore_with_me.exception;

public class UnsupportedException extends RuntimeException {
    private final String error;

    public UnsupportedException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
