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

    public Optional<Record> findByIdAndUser(Long id, User user) {
        return recordRepository.findByIdAndUser(id, user);
    }

    public Optional<Record> updateRecord(Long id, Record updatedRecord, User user) {
        return recordRepository.findByIdAndUser(id, user).map(originalRecord -> {

            // ✅ null チェックを追加（Flutter 側が null を送る可能性に備える）
            if (updatedRecord.getDate() == null) {
                updatedRecord.setDate(originalRecord.getDate());
            }

            originalRecord.setDate(updatedRecord.getDate());
            originalRecord.setExercise(updatedRecord.getExercise());
            originalRecord.setWeight(updatedRecord.getWeight());
            originalRecord.setReps(updatedRecord.getReps());
            originalRecord.setSets(updatedRecord.getSets());
            originalRecord.setMemo(updatedRecord.getMemo());

            return recordRepository.save(originalRecord);
        });
    }

    public boolean deleteRecord(Long id, User user) {
        return recordRepository.findByIdAndUser(id, user).map(record -> {
            recordRepository.delete(record);
            return true;
        }).orElse(false);
    }

    public List<Record> findAllByUser(User user) {
        return recordRepository.findByUser(user);
    }

    public Record saveRecord(Record record, User user) {
        record.setUser(user);
        return recordRepository.save(record);
    }
}
