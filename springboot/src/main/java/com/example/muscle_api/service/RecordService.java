package com.example.muscle_api.service;

import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public List<Record> findByUserAndDate(User user, LocalDate date) {
        return recordRepository.findByUserAndDate(user, date);
    }

    public int calculateStreak(User user) {
        int streak = 0;
        LocalDate today = LocalDate.now();

        while (true) {
            LocalDate targetDate = today.minusDays(streak);
            boolean hasRecord = recordRepository.existsByUserAndDate(user, targetDate);

            if (hasRecord) {
                streak++;
            } else {
                break;
            }
        }

        return streak;
    }

    // ✅ 追加：本人の記録を1件取得
    public Optional<Record> findByIdAndUser(Long id, User user) {
        return recordRepository.findByIdAndUser(id, user);
    }

    // ✅ 追加：記録の更新
    public Optional<Record> updateRecord(Long id, Record updatedRecord, User user) {
        return recordRepository.findByIdAndUser(id, user).map(record -> {
            record.setDate(updatedRecord.getDate());
            record.setExercise(updatedRecord.getExercise());
            record.setWeight(updatedRecord.getWeight());
            record.setReps(updatedRecord.getReps());
            record.setSets(updatedRecord.getSets());
            record.setMemo(updatedRecord.getMemo());
            return recordRepository.save(record);
        });
    }

    // ✅ 追加：記録の削除
    public boolean deleteRecord(Long id, User user) {
        return recordRepository.findByIdAndUser(id, user).map(record -> {
            recordRepository.delete(record);
            return true;
        }).orElse(false);
    }
}
