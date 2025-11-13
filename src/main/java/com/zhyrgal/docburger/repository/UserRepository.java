package com.zhyrgal.docburger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zhyrgal.docburger.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}