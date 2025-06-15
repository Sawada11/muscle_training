package com.example.muscle_api.controller;

import com.example.muscle_api.dto.UserDto;
import com.example.muscle_api.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return new UserDto(userDetails.getUser());
    }
}
