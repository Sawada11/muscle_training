package com.example.muscle_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryDto {
    private int totalLoad; // 総負荷量 = weight × reps × sets の合計
}
