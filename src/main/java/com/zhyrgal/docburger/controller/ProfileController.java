package com.zhyrgal.docburger.controller;

import com.zhyrgal.docburger.model.Profile;
import com.zhyrgal.docburger.model.User;
import com.zhyrgal.docburger.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // Получить профиль текущего пользователя
    @GetMapping("/me")
    public ResponseEntity<Profile> getMyProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Profile profile = profileService.getProfile(user);
        return ResponseEntity.ok(profile);
    }

    // Обновить профиль текущего пользователя
    @PutMapping("/me")
    public ResponseEntity<Profile> updateMyProfile(Authentication authentication,
                                                   @RequestBody Profile updatedProfile) {
        User user = (User) authentication.getPrincipal();
        Profile profile = profileService.updateProfile(user, updatedProfile);
        return ResponseEntity.ok(profile);
    }
}
