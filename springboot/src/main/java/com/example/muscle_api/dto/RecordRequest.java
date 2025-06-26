package com.example.muscle_api.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordRequest {

    @NotNull(message = "日付は必須です")
    private LocalDate date;

    @NotBlank(message = "種目は必須です")
    private String exercise;

    @Min(value = 1, message = "重量は1kg以上である必要があります")
    private int weight;

    @Min(value = 1, message = "回数は1回以上である必要があります")
    private int reps;

    @Min(value = 1, message = "セット数は1セット以上である必要があります")
    private int sets;

    private String memo;
}
