package com.example.muscle_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String exercise;

    private int weight;

    private int reps;

    private int sets;

    private String memo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // ユーザーエンティティとのリレーション

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
