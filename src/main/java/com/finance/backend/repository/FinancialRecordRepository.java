package com.finance.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.RecordType;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByCategory(String category);
}