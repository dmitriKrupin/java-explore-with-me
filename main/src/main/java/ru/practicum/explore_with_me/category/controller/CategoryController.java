package ru.practicum.explore_with_me.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        log.info("Получаем GET запрос к эндпойнту /compilations");
        return categoryService.getAllCategories();
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("Получаем GET запрос к эндпойнту /compilations");
        return categoryService.getCategoryById(catId);
    }
}
