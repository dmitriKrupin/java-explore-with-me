package ru.practicum.explore_with_me.exception.dto;

import java.util.List;

public class ApiError {
    //Сведения об ошибке
    private List<String> errors;
    //todo: example: List [] Список стектрейсов или описания ошибок
    // string example: [] Список стектрейсов или описания ошибок
    private String message;
    private String reason;
    private String status;
    //todo: example: FORBIDDEN Код статуса HTTP-ответа Enum: Array [ 68 ]
    private String timestamp;
}
