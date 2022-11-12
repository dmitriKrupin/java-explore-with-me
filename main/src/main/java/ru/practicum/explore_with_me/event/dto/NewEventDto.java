package ru.practicum.explore_with_me.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explore_with_me.event.model.Location;

@Data
@AllArgsConstructor
public class NewEventDto {
    //Новое событие
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
