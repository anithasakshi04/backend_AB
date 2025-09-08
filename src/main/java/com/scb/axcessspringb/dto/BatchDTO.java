package com.scb.axcessspringb.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

public class BatchDTO {
    private String id;
    private String name;
    private String status;
    private BigDecimal totalAmount;
    private LocalDate paymentDate;
    private String paymentType;
    private String currency;
    private String debitAccount;
    private String accountType;
    private List<PayrollEntryDTO> entries;

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

    public List<PayrollEntryDTO> getEntries() { return entries; }
    public void setEntries(List<PayrollEntryDTO> entries) { this.entries = entries; }
}