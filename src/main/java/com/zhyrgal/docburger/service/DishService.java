package com.zhyrgal.docburger.service;

import com.zhyrgal.docburger.model.Dish;
import com.zhyrgal.docburger.model.Category;
import com.zhyrgal.docburger.repository.CategoryRepository;
import com.zhyrgal.docburger.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Optional<Dish> getById(Long id) {
        return dishRepository.findById(id);
    }

    public Dish create(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish update(Long id, Dish updatedDish) {
        return dishRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedDish.getName());
                    existing.setPrice(updatedDish.getPrice());
                    existing.setDescription(updatedDish.getDescription());
                    existing.setImage(updatedDish.getImage());
                    existing.setCategory(updatedDish.getCategory());
                    return dishRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    public List<Dish> getByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return dishRepository.findByCategory(category);
    }

    public List<Dish> getByCategoryName(String categoryName) {
        return dishRepository.findByCategory_Name(categoryName);
    }

    // -------------------- Новый метод --------------------
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
