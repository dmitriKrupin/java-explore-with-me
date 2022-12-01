package ru.practicum.ewm.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    //Сведения об ошибке
    private List<String> errors;
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}
