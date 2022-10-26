package ru.practicum.explore_with_me.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    //Подборка событий
    private List<Long> events;
    private Boolean pinned;
    private String title;
}
