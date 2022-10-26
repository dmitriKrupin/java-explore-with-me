package ru.practicum.explore_with_me.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.category.service.CategoryService;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PatchMapping
    private CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/categories");
        return categoryService.updateCategory(categoryDto);
    }

    @PostMapping
    private CategoryDto addCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/categories");
        return categoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    private void deleteCategory(@PathVariable Long catId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/categories/{}", catId);
        categoryService.deleteCategory(catId);
    }
}
