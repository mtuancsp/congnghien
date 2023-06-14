package com.example.springboot.repository;

import com.example.springboot.model.ReturnRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRecordRepository extends JpaRepository<ReturnRecord, Long> {
    boolean existsByBorrowCode(String borrowCode);
}
