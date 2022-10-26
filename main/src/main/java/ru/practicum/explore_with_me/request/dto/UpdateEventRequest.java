package ru.practicum.explore_with_me.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEventRequest {
    //Данные для изменения информации о событии
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private String title;
}
