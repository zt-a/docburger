package com.zhyrgal.docburger.controller;

import com.zhyrgal.docburger.config.JwtService;
import com.zhyrgal.docburger.model.Role;
import com.zhyrgal.docburger.model.User;
import com.zhyrgal.docburger.service.ProfileService;
import com.zhyrgal.docburger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ProfileService profileService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setRole(Role.USER);
        User savedUser = userService.registerUser(user);

        // Создаем пустой профиль для нового пользователя
        profileService.createEmptyProfile(savedUser);

        return savedUser;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = (User) userService.loadUserByUsername(username);
            String token = jwtService.generateToken(user);

            // Возвращаем токен + данные пользователя без пароля
            return Map.of(
                    "token", token,
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole().name()
            );

        } catch (AuthenticationException e) {
            throw new RuntimeException("Неверный логин или пароль");
        }
    }
}
