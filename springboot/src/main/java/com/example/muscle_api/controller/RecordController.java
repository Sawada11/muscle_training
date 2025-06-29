package com.example.muscle_api.controller;

import com.example.muscle_api.entity.Record;
import com.example.muscle_api.security.CustomUserDetails;
import com.example.muscle_api.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // 🔹 POST: 記録を追加 ← ✅ 追加
    @PostMapping
    public ResponseEntity<?> createRecord(
            @RequestBody Record record,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Record saved = recordService.saveRecord(record, userDetails.getUser());
        return ResponseEntity.ok(saved);
    }

    // 🔹 GET: 全記録（ログインユーザー）
    @GetMapping
    public ResponseEntity<List<Record>> getRecords(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Record> records = recordService.findAllByUser(userDetails.getUser());
        return ResponseEntity.ok(records);
    }

    // 🔹 GET: 単一の記録を取得
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Optional<Record> record = recordService.findByIdAndUser(id, userDetails.getUser());
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 PUT: 記録を更新
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(
            @PathVariable Long id,
            @RequestBody Record updatedRecord,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Optional<Record> result = recordService.updateRecord(id, updatedRecord, userDetails.getUser());
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 DELETE: 記録を削除
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean deleted = recordService.deleteRecord(id, userDetails.getUser());
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
