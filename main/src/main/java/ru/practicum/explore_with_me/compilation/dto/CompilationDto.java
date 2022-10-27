package ru.practicum.explore_with_me.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explore_with_me.event.model.Event;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {
    //Подборка событий
    private List<Event> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
