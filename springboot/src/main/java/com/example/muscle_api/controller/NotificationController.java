package com.example.muscle_api.controller;

import com.example.muscle_api.dto.TokenRequest;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class NotificationController {

    private final UserRepository userRepository;

    @PostMapping("/token")
    public ResponseEntity<?> saveFcmToken(
            @RequestBody TokenRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));

        user.setFcmToken(request.getToken());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
