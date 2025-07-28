package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long recipientId;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status; // "COMPLETED" or "FAILED"

    @Column(nullable = true)
    private Float incentive; // Optional, can be null if no incentive is applied

    protected TransactionRecord() {
    }

    public TransactionRecord(Long senderId, Long recipientId, Float amount, String status,Float incentive) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.status = status;
        this.incentive = incentive;
        // Set the timestamp to the current time when the transaction is created
        this.timestamp = LocalDateTime.now();
    }

    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public Float getIncentive() {
        return incentive;
    }

    public void setIncentive(Float incentiveAmount) {
        this.incentive = incentiveAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("TransactionRecord[id=%d, senderId=%d, recipientId=%d, amount=%.2f, status=%s, timestamp=%s]",
                id, senderId, recipientId, amount, status, timestamp);
    }
}