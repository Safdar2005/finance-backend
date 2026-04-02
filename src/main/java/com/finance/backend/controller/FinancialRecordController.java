package com.finance.backend.controller;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.RecordType;
import com.finance.backend.service.FinancialRecordService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    // ================= CREATE =================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FinancialRecord create(@RequestBody FinancialRecord record) {
        return service.create(record);
    }

    // ================= GET ALL =================
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping
    public List<FinancialRecord> getAll() {
        return service.getAll();
    }

    // ================= UPDATE =================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public FinancialRecord update(
            @PathVariable Long id,
            @RequestBody FinancialRecord record) {

        return service.update(id, record);
    }

    // ================= DELETE =================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ================= FILTER =================
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/filter")
    public List<FinancialRecord> filter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) RecordType type) {

        return service.filter(category, type);
    }

    // ================= SUMMARY =================

    // Total Income
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/income")
    public double totalIncome() {
        return service.totalIncome();
    }

    // Total Expense
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/expense")
    public double totalExpense() {
        return service.totalExpense();
    }

    // Net Balance
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/net")
    public double netBalance() {
        return service.netBalance();
    }

    // Category-wise summary
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/category")
    public Map<String, Double> categorySummary() {
        return service.categorySummary();
    }
}