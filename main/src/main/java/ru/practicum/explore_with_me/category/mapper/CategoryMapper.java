package ru.practicum.explore_with_me.category.mapper;

import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.category.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category entry : categories) {
            categoryDtoList.add(CategoryMapper.toCategoryDto(entry));
        }
        return categoryDtoList;
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName());
    }

    public static Category toNewCategory(NewCategoryDto newCategoryDto) {
        return new Category(newCategoryDto.getId(), newCategoryDto.getName());
    }
}
