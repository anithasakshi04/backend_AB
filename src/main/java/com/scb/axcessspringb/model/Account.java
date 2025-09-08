//package com.scb.axcessspringb.model;
//
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "accounts")
//public class Account {
//
//    @Id
//    private String accountNumber;
//
//    private String accountName;
//    private String accountType;
//    private BigDecimal balance;
//    private String currency;
//
//    // One account can have many transactions
//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Transaction> transactions = new ArrayList<>();
//
//    // -------- Constructors --------
//    public Account() {}
//
//    public Account(String accountNumber, String accountName, String accountType,
//                   BigDecimal balance, String currency) {
//        this.accountNumber = accountNumber;
//        this.accountName = accountName;
//        this.accountType = accountType;
//        this.balance = balance;
//        this.currency = currency;
//    }
//
//    // -------- Getters & Setters --------
//    public String getAccountNumber() {
//        return accountNumber;
//    }
//    public void setAccountNumber(String accountNumber) {
//        this.accountNumber = accountNumber;
//    }
//
//    public String getAccountName() {
//        return accountName;
//    }
//    public void setAccountName(String accountName) {
//        this.accountName = accountName;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
//
//    public BigDecimal getBalance() {
//        return balance;
//    }
//    public void setBalance(BigDecimal balance) {
//        this.balance = balance;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    public List<Transaction> getTransactions() {
//        return transactions;
//    }
//    public void setTransactions(List<Transaction> transactions) {
//        this.transactions = transactions;
//    }
//
//    // Helper method to add transaction
//    public void addTransaction(Transaction txn) {
//        transactions.add(txn);
//        txn.setAccount(this);
//    }
//
//    // Helper method to remove transaction
//    public void removeTransaction(Transaction txn) {
//        transactions.remove(txn);
//        txn.setAccount(null);
//    }
//}

package com.scb.axcessspringb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String accountNumber;

    private String accountName;
    private String accountType;
    private BigDecimal balance;
    private String currency;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {}

    public Account(String accountNumber, String accountName, String accountType, BigDecimal balance, String currency) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
    }

    // Getters & setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public void addTransaction(Transaction txn) {
        this.transactions.add(txn);
        txn.setAccount(this);
    }
}
