package ru.practicum.explore_with_me.exception.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explore_with_me.exception.*;
import ru.practicum.explore_with_me.exception.dto.ApiError;

import java.util.List;

@RestControllerAdvice
@Data
public class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public BadRequestException badRequestException(final BadRequestException e) {
        return new BadRequestException(
                String.format("Ошибка с полем \"%s\".", e.getParameter())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ForbiddenException forbiddenException(final ForbiddenException e) {
        return new ForbiddenException(
                String.format("Ошибка с полем \"%s\".", e.getParameter())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ResponseEntity<ApiError> notFoundException(final NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                //    private List<String> errors;
                //    //todo: example: List [] Список стектрейсов или описания ошибок
                //    // string example: [] Список стектрейсов или описания ошибок
                //    private String message;
                //    private String reason;
                //    private String status;
                //    //todo: example: FORBIDDEN Код статуса HTTP-ответа Enum: Array [ 68 ]
                //    private String timestamp;
                .body(new ApiError(List.of(e.toString())));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) //409
    public ConflictException conflictException(final ConflictException e) {
        return new ConflictException(
                String.format("Ошибка с полем \"%s\".", e.getParameter())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public InternalServerErrorException unsupportedException(final InternalServerErrorException e) {
        return new InternalServerErrorException(e.getError());
    }

}
