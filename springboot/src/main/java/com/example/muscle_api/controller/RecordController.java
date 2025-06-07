package com.example.muscle_api.controller;


import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<?> addRecord(
            @RequestBody Record record,
            @AuthenticationPrincipal User user
    ) {
        // 認証ユーザーの情報が取得できているか確認
        if (user == null) {
            return ResponseEntity.status(401).body("認証が必要です");
        }

        Record saved = recordService.saveRecord(record, user);
        return ResponseEntity.ok(saved);
    }
}