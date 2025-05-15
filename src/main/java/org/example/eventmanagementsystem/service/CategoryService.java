package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.eventmanagementsystem.dto.category.AddCategoryDto;
import org.example.eventmanagementsystem.dto.category.CategoryDto;
import org.example.eventmanagementsystem.dto.category.UpdatedCategoryDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.CategoryMapper;
import org.example.eventmanagementsystem.model.Category;
import org.example.eventmanagementsystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() {
        return categoryMapper.categoriesToCategoriesDto(categoryRepository.findAll());
    }

    public CategoryDto getById(Long id) throws ResourceNotFoundException {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category can not found");
            return new ResourceNotFoundException("Category can not found");
        }));
    }

    public CategoryDto add(AddCategoryDto addCategoryDto) {
        Category category = categoryRepository.save(categoryMapper.addCategoryDtoToCategory(addCategoryDto));
        return categoryMapper.categoryToCategoryDto(category);
    }

    public CategoryDto update(Long id, UpdatedCategoryDto updatedCategoryDto) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category can not found");
            return new ResourceNotFoundException("Category can not found");
        });
        category.setName(updatedCategoryDto.name());
        Category saved = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(saved);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}

