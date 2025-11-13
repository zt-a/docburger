package com.zhyrgal.docburger.repository;

import com.zhyrgal.docburger.model.Profile;
import com.zhyrgal.docburger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
