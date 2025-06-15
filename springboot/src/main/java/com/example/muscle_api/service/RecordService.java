package com.example.muscle_api.service;

import com.example.muscle_api.dto.RecordDto;
import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record saveRecord(Record record, User user) {
        record.setUser(user);
        return recordRepository.save(record);
    }

    public List<RecordDto> getRecordsByUser(User user) {
        return recordRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RecordDto convertToDto(Record record) {
        return RecordDto.builder()
                .id(record.getId())
                .date(record.getDate())
                .exercise(record.getExercise())
                .weight(record.getWeight())
                .reps(record.getReps())
                .sets(record.getSets())
                .memo(record.getMemo())
                .createdAt(record.getCreatedAt())
                .userName(record.getUser().getName())
                .build();
    }
}
