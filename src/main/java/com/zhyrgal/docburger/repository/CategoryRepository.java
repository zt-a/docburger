package com.zhyrgal.docburger.repository;

import com.zhyrgal.docburger.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
