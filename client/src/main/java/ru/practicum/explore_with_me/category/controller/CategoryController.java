package ru.practicum.explore_with_me.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.explore_with_me.category.client.CategoryClient;

@Slf4j
@Controller
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryClient categoryClient;

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        log.info("Получаем GET запрос к эндпойнту /categories");
        return categoryClient.getAllCategories();
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long catId) {
        log.info("Получаем GET запрос к эндпойнту /categories/{}", catId);
        return categoryClient.getCategoryById(catId);
    }
}
