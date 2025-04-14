package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.category.AddCategoryDto;
import org.example.eventmanagementsystem.dto.category.CategoryDto;
import org.example.eventmanagementsystem.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoriesToCategoriesDto(List<Category> categories);
    Category addCategoryDtoToCategory(AddCategoryDto addCategoryDto);
}
