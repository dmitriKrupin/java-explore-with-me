package ru.practicum.explore_with_me.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.user.dto.UserShortDto;

@Data
@AllArgsConstructor
public class EventShortDto {
    //Краткая информация о событии
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
