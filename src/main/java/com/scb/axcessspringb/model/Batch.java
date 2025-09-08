package com.scb.axcessspringb.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "batches")
public class Batch {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String status = "draft";

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private LocalDate paymentDate;

    private String paymentType;
    private String currency = "INR";
    private String debitAccount;
    private String accountType;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayrollEntry> entries;

    // Constructor for sample data
    public Batch(String id, String name, String status, String paymentType, String debitAccount, String accountType) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.paymentType = paymentType;
        this.debitAccount = debitAccount;
        this.accountType = accountType;
    }

    // Default constructor required by JPA
    public Batch() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDebitAccount() { return debitAccount; }
    public void setDebitAccount(String debitAccount) { this.debitAccount = debitAccount; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public List<PayrollEntry> getEntries() { return entries; }
    public void setEntries(List<PayrollEntry> entries) { this.entries = entries; }

    public Object getBatchId() {
        return null;
    }
}


//@Entity
//@Table(name = "batches")
//public class Batch {
//
//    @Id
//    private String batchId;
//
//    private String debitAccount;
//
//    // Join with accounts via debitAccount
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "debitAccount", referencedColumnName = "accountNumber", insertable = false, updatable = false)
//    private Account account;
//
//    // One batch → many payroll entries
//    @OneToMany(mappedBy = "batch", fetch = FetchType.LAZY)
//    private List<PayrollEntry> payrollEntries;
//}
