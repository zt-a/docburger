package com.zhyrgal.docburger.service;

import com.zhyrgal.docburger.model.Profile;
import com.zhyrgal.docburger.model.User;
import com.zhyrgal.docburger.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    // Создает пустой профиль для нового пользователя
    public Profile createEmptyProfile(User user) {
        Profile profile = Profile.builder()
                .user(user)
                .build();
        return profileRepository.save(profile);
    }

    // Получение профиля пользователя
    public Profile getProfile(User user) {
        return profileRepository.findByUser(user)
                .orElseGet(() -> createEmptyProfile(user));
    }

    // Обновление профиля
    public Profile updateProfile(User user, Profile updatedProfile) {
        Profile profile = getProfile(user);
        profile.setAddress(updatedProfile.getAddress());
        profile.setCity(updatedProfile.getCity());
        profile.setPhone(updatedProfile.getPhone());
        profile.setApartment(updatedProfile.getApartment());
        profile.setIntercom(updatedProfile.getIntercom());
        return profileRepository.save(profile);
    }
}
