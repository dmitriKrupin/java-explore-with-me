package ru.practicum.explore_with_me.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    //Подборка событий
    @NotNull
    @NotBlank
    @NotEmpty
    private String title;
    private List<Long> events;
    private Boolean pinned;
}
