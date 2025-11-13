package com.zhyrgal.docburger.config;

import com.zhyrgal.docburger.model.Category;
import com.zhyrgal.docburger.model.User;
import com.zhyrgal.docburger.model.Role;
import com.zhyrgal.docburger.repository.CategoryRepository;
import com.zhyrgal.docburger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // --- 1. Инициализация категорий ---
        if (categoryRepository.count() == 0) {
            String[] categories = {"Бургеры", "Шаурма", "Комбо", "Десерты", "Напитки", "Соусы", "Салаты"};
            for (String name : categories) {
                categoryRepository.save(Category.builder().name(name).build());
            }
            System.out.println("✅ Категории успешно добавлены");
        }

        // --- 2. Инициализация администратора ---
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@docburger.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Администратор создан: admin / admin123");
        } else {
            System.out.println("ℹ️ Администратор уже существует, пропускаем создание");
        }
    }
}
