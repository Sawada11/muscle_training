package com.example.muscle_api.controller;

import com.example.muscle_api.dto.RecordDto;
import com.example.muscle_api.entity.Record;
import com.example.muscle_api.security.CustomUserDetails;
import com.example.muscle_api.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<?> addRecord(
            @RequestBody Record record,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Record saved = recordService.saveRecord(record, userDetails.getUser());
        return ResponseEntity.ok("保存されました: ID = " + saved.getId());
    }

    @GetMapping
    public ResponseEntity<List<RecordDto>> getRecords(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<RecordDto> records = recordService.getRecordsByUser(userDetails.getUser());
        return ResponseEntity.ok(records);
    }
}
