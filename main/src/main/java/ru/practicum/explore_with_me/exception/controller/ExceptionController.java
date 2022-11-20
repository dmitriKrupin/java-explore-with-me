package ru.practicum.explore_with_me.exception.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explore_with_me.exception.*;
import ru.practicum.explore_with_me.exception.dto.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestControllerAdvice
@Data
public class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ResponseEntity<ApiError> badRequestException(final BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "Only pending or canceled events can be changed",
                        "BAD_REQUEST",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ResponseEntity<ApiError> forbiddenException(final ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "For the requested operation the conditions are not met.", //e.getCause().toString(),
                        "FORBIDDEN",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
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
    @ResponseStatus(HttpStatus.CONFLICT) //409
    public ResponseEntity<ApiError> conflictException(final ConflictException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getParameter(),
                        "Integrity constraint has been violated",
                        "String status",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ResponseEntity<ApiError> unsupportedException(final InternalServerErrorException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        List.of(e.toString()),
                        e.getError(),
                        "Error occurred",
                        "INTERNAL_SERVER_ERROR",
                        LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

}
