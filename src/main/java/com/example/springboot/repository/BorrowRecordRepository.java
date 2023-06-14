package com.example.springboot.repository;

import com.example.springboot.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBorrowCode(String borrowCode);
}
