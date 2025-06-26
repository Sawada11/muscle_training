package com.example.muscle_api.repository;

import com.example.muscle_api.entity.Record;
import com.example.muscle_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUser(User user);
    List<Record> findByUserAndDate(User user, LocalDate date);
    boolean existsByUserAndDate(User user, LocalDate date);

    // ✅ 追加：特定IDの記録が本人のものであるか確認用
    Optional<Record> findByIdAndUser(Long id, User user);
}
