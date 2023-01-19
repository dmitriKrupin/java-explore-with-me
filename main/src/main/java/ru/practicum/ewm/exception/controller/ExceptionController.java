package ru.practicum.ewm.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.exception.dto.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> badRequestException(final BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "For the requested operation the conditions are not met.",
                        "BAD_REQUEST",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> forbiddenException(final ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "For the requested operation the conditions are not met.",
                        "FORBIDDEN",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundException(final NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "The required object was not found.",
                        "NOT_FOUND",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> conflictException(final ConflictException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "Integrity constraint has been violated.",
                        "CONFLICT",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> unsupportedException(final InternalServerErrorException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getError(),
                        "Error occurred.",
                        "INTERNAL_SERVER_ERROR",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

}
