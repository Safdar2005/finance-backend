package com.finance.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    private String category;

    private LocalDate date;

    private String description;

    // Constructors
    public FinancialRecord() {}

    public FinancialRecord(double amount, RecordType type, String category, LocalDate date, String description) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public RecordType getType() { return type; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setType(RecordType type) { this.type = type; }
    public void setCategory(String category) { this.category = category; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
}