package com.example.muscle_api.service;

import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import com.example.muscle_api.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record saveRecord(Record record, User user) {
        record.setUser(user); // 認証ユーザーをセット
        return recordRepository.save(record);
    }
}