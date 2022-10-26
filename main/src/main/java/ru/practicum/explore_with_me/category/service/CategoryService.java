package ru.practicum.explore_with_me.category.service;

import ru.practicum.explore_with_me.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long catId);
}
