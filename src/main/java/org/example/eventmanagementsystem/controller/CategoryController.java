package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.category.AddCategoryDto;
import org.example.eventmanagementsystem.dto.category.CategoryDto;
import org.example.eventmanagementsystem.dto.category.UpdatedCategoryDto;
import org.example.eventmanagementsystem.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> add(@RequestBody AddCategoryDto addCategoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.add(addCategoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody UpdatedCategoryDto updatedCategoryDto) {
        return ResponseEntity.ok(categoryService.update(id, updatedCategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
