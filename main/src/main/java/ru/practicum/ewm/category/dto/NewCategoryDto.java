package ru.practicum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewCategoryDto {
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
}
