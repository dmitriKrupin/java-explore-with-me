package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.event.model.Location;

@Data
@AllArgsConstructor
public class AdminUpdateEventRequest {
    //Информация для редактирования события администратором.
    // Все поля необязательные. Значение полей не валидируется.
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
}
