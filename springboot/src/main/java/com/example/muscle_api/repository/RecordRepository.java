package com.example.muscle_api.repository;

import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUser(User user);
}
