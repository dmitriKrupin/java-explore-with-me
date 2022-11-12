package ru.practicum.explore_with_me.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCategoryDto {
    //Данные для добавления новой категории
    private Long id;
    private String name;
}
