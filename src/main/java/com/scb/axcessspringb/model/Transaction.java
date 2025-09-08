package com.scb.axcessspringb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "transactions")
//public class Transaction {
//
//    @Id
//    private String transactionId;
//
//    private BigDecimal amount;
//    private String type; // DEBIT or CREDIT
//    private String description;
//
//    private String batchId; // reference to batch, no JPA relation needed
//    private  String status;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "accountNumber", referencedColumnName = "accountNumber")
//    private Account account;
//
//    // getters and setters...
//
//
//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(String transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getBatchId() {
//        return batchId;
//    }
//
//    public void setBatchId(String batchId) {
//        this.batchId = batchId;
//    }
//
//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String id; // or other PK type; match your DB

    private BigDecimal amount;
    private String type;      // "DEBIT" or "CREDIT"
    private String status;    // e.g. "Approved"
    private String counterparty;
    private LocalDateTime dateTime;

    // link to Batch (no change to Batch entity required)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id", insertable = true, updatable = false)
    private Batch batch;

    // link to Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", referencedColumnName = "accountNumber", insertable = true, updatable = true)
    private Account account;

    public Transaction() {}

    public Transaction(String id, BigDecimal amount, String type, String status, String counterparty, LocalDateTime dateTime) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.counterparty = counterparty;
        this.dateTime = dateTime;
    }

    // Getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCounterparty() { return counterparty; }
    public void setCounterparty(String counterparty) { this.counterparty = counterparty; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}
