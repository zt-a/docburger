package com.zhyrgal.docburger.controller;

import com.zhyrgal.docburger.model.Dish;
import com.zhyrgal.docburger.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    // Папка для хранения изображений (можно вынести в application.properties)
    private final String UPLOAD_FOLDER = "uploads/images/";

    // ------------------- Получение всех блюд -------------------
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Dish> dishes = dishService.getAll();
        if (dishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No dishes found"));
        }
        return ResponseEntity.ok(dishes);
    }

    // ------------------- Получение блюда по ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Dish> dishOpt = dishService.getById(id);
        if (dishOpt.isPresent()) {
            return ResponseEntity.ok(dishOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Dish not found"));
        }
    }

    // ------------------- Получение блюд по категории -------------------
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getByCategoryId(@PathVariable Long categoryId) {
        List<Dish> dishes = dishService.getByCategoryId(categoryId);
        if (dishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No dishes found in this category"));
        }
        return ResponseEntity.ok(dishes);
    }

    // ------------------- Создание блюда с изображением -------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Dish> create(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(required = false) String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        String imageUrl = null;

        // Сохраняем изображение на сервер
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_FOLDER);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.write(filePath, imageFile.getBytes());
                imageUrl = "/" + UPLOAD_FOLDER + filename;
            } catch (Exception e) {
                throw new RuntimeException("Failed to store image: " + e.getMessage());
            }
        }

        // Создаём блюдо
        Dish dish = Dish.builder()
                .name(name)
                .price(price)
                .description(description)
                .image(imageUrl)
                .category(dishService.getCategoryById(categoryId))
                .build();

        Dish savedDish = dishService.create(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    // ------------------- Обновление блюда -------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestParam String name,
                                    @RequestParam double price,
                                    @RequestParam(required = false) String description,
                                    @RequestParam("categoryId") Long categoryId,
                                    @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            Dish existingDish = dishService.getById(id)
                    .orElseThrow(() -> new RuntimeException("Dish not found"));

            existingDish.setName(name);
            existingDish.setPrice(price);
            existingDish.setDescription(description);
            existingDish.setCategory(dishService.getCategoryById(categoryId));

            // Если прислали новый файл, заменяем
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_FOLDER);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.write(filePath, imageFile.getBytes());
                existingDish.setImage("/" + UPLOAD_FOLDER + filename);
            }

            Dish updatedDish = dishService.update(id, existingDish);
            return ResponseEntity.ok(updatedDish);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating dish", "error", e.getMessage()));
        }
    }

    // ------------------- Удаление блюда -------------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            dishService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Dish deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
