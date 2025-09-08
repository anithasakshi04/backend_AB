package com.scb.axcessspringb.dto;

import java.math.BigDecimal;

public class PayrollEntryDTO {
    private Long id;
    private String method;
    private String payeeDetails;
    private String payeeName;
    private String bankDetails;
    private String yourReference;
    private String paymentReference;
    private BigDecimal amount;
    private String notes;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getPayeeDetails() { return payeeDetails; }
    public void setPayeeDetails(String payeeDetails) { this.payeeDetails = payeeDetails; }

    public String getPayeeName() { return payeeName; }
    public void setPayeeName(String payeeName) { this.payeeName = payeeName; }

    public String getBankDetails() { return bankDetails; }
    public void setBankDetails(String bankDetails) { this.bankDetails = bankDetails; }

    public String getYourReference() { return yourReference; }
    public void setYourReference(String yourReference) { this.yourReference = yourReference; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}