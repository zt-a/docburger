package com.zhyrgal.docburger.repository;

import com.zhyrgal.docburger.model.Category;
import com.zhyrgal.docburger.model.Dish;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {

    // Получить все блюда по объекту категории
    List<Dish> findByCategory(Category category);

    // Или по имени категории
    List<Dish> findByCategory_Name(String name);
}
