package com.finance.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.RecordType;
import com.finance.backend.repository.FinancialRecordRepository;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository repository;

    public FinancialRecordService(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    // ================= CREATE =================
    public FinancialRecord create(FinancialRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Record cannot be null");
        }

        if (record.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        return repository.save(record);
    }

    // ================= GET ALL =================
    public List<FinancialRecord> getAll() {
        return repository.findAll();
    }

    // ================= UPDATE =================
    public FinancialRecord update(Long id, FinancialRecord record) {

        FinancialRecord existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        //  validation add
        if (record.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        existing.setAmount(record.getAmount());
        existing.setType(record.getType());
        existing.setCategory(record.getCategory());
        existing.setDate(record.getDate());
        existing.setDescription(record.getDescription());

        return repository.save(existing);
    }

    // ================= DELETE =================
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!repository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }

        repository.deleteById(id);
    }

    // ================= FILTER =================
    public List<FinancialRecord> filter(String category, RecordType type) {

        //  both filter
        if (category != null && !category.isBlank() && type != null) {
            return repository.findAll().stream()
                    .filter(r ->
                            r.getCategory() != null &&
                                    r.getCategory().equalsIgnoreCase(category) &&
                                    r.getType() == type
                    )
                    .toList();
        }

        //  category filter safe
        if (category != null && !category.isBlank()) {
            return repository.findByCategory(category);
        }

        //  type filter
        if (type != null) {
            return repository.findByType(type);
        }

        return repository.findAll();
    }

    // ================= TOTAL INCOME =================
    public double totalIncome() {
        return repository.findByType(RecordType.INCOME)
                .stream()
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    // ================= TOTAL EXPENSE =================
    public double totalExpense() {
        return repository.findByType(RecordType.EXPENSE)
                .stream()
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    // ================= NET BALANCE =================
    public double netBalance() {
        return totalIncome() - totalExpense();
    }

    // ================= CATEGORY SUMMARY =================
    public Map<String, Double> categorySummary() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCategory() != null ? r.getCategory() : "Unknown",
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));
    }
}