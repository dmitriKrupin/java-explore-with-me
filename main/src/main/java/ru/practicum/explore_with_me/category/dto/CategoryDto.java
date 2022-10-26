package ru.practicum.explore_with_me.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    //Категория
    private Long id;
    private String name;
}
