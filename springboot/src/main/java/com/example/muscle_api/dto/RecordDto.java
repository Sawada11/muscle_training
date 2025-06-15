package com.example.muscle_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RecordDto {
    private Long id;
    private LocalDate date;
    private String exercise;
    private int weight;
    private int reps;
    private int sets;
    private String memo;
    private LocalDateTime createdAt;
    private String userName;
}
