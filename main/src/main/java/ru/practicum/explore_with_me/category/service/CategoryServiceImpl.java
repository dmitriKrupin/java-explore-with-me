package ru.practicum.explore_with_me.category.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.mapper.CategoryMapper;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.category.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryMapper.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + catId + " нет"));
        return CategoryMapper.toCategoryDto(category);
    }
}
