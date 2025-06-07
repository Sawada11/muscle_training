package com.example.muscle_api.service;

import com.example.muscle_api.dto.AuthRequest;
import com.example.muscle_api.dto.LoginRequest;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.repository.UserRepository;
import com.example.muscle_api.util.JwtUtil; // ← JWT生成クラス
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // ← 追加！

    // 登録処理
    public void registerUser(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("すでに登録されています");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    // ログイン処理
    public String authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("ユーザーが存在しません"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("パスワードが一致しません");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
