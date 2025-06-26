package com.example.muscle_api.controller;

import com.example.muscle_api.dto.StreakDto;
import com.example.muscle_api.dto.SummaryDto;
import com.example.muscle_api.entity.Record;
import com.example.muscle_api.security.CustomUserDetails;
import com.example.muscle_api.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final RecordService recordService;

    @GetMapping("/today")
    public ResponseEntity<SummaryDto> getTodayTotalLoad(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Record> todayRecords = recordService.findByUserAndDate(userDetails.getUser(), LocalDate.now());

        int totalLoad = todayRecords.stream()
                .mapToInt(r -> r.getWeight() * r.getReps() * r.getSets())
                .sum();

        return ResponseEntity.ok(new SummaryDto(totalLoad));
    }

    @GetMapping("/streak")
    public ResponseEntity<StreakDto> getStreak(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int streakCount = recordService.calculateStreak(userDetails.getUser());
        return ResponseEntity.ok(new StreakDto(streakCount));
    }
}
