package com.example.muscle_api.controller;

import com.example.muscle_api.dto.AuthRequest;
import com.example.muscle_api.dto.LoginRequest;
import com.example.muscle_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ユーザー登録
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("登録完了");
    }

    // ログイン（JWT発行）
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
