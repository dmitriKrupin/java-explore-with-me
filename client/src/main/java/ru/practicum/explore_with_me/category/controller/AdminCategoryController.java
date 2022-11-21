package ru.practicum.explore_with_me.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.category.client.AdminCategoryClient;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;

import javax.validation.constraints.PositiveOrZero;

@Controller
@Slf4j
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryClient adminCategoryClient;

    @PatchMapping
    private ResponseEntity<Object> updateCategory(
            @Validated @RequestBody CategoryDto categoryDto) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/categories");
        return adminCategoryClient.updateCategory(categoryDto);
    }

    @PostMapping
    private ResponseEntity<Object> addCategory(
            @Validated @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/categories");
        return adminCategoryClient.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    private ResponseEntity<Object> deleteCategory(
            @Validated @PositiveOrZero @PathVariable Long catId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/categories/{}", catId);
        return adminCategoryClient.deleteCategory(catId);
    }
}
