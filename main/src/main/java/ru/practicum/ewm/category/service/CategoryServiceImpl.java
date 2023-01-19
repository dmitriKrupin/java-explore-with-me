package ru.practicum.ewm.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
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
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + catId + " нет"));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = new Category();
        Boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName) {
            throw new ConflictException("Категория с таким именем "
                    + categoryDto.getName() + " уже есть в базе!");
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toNewCategory(newCategoryDto);
        if (categoryRepository.countByNameLike(newCategoryDto.getName()) > 0) {
            throw new ConflictException("Категория с таким именем " +
                    newCategoryDto.getName() + " уже есть в базе!");
        }
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + catId + " нет"));
        categoryRepository.delete(category);
    }
}
