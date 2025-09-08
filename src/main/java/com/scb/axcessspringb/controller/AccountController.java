////package com.scb.axcessspringb.controller;
////
////import com.scb.axcessspringb.model.Account;
////import com.scb.axcessspringb.model.Transaction;
////import com.scb.axcessspringb.service.AccountService;
////import com.scb.axcessspringb.service.TransactionService;
////import org.springframework.web.bind.annotation.*;
////
////import java.math.BigDecimal;
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/account")
////public class AccountController {
////
////    private final AccountService accountService;
////    private final TransactionService transactionService;
////
////    public AccountController(AccountService accountService, TransactionService transactionService) {
////        this.accountService = accountService;
////        this.transactionService = transactionService;
////    }
////
//////    @GetMapping("/accounts")
//////    public List<String> getAllAccounts() {
//////        return accountService.getAllAccountNumbers(); // ✅ existing method
//////    }
////
////
////    @GetMapping("/{accountNo}/balance")
////    public BigDecimal getBalance(@PathVariable String accountNo) {
////        return accountService.getBalance(accountNo);
////    }
////
////    @GetMapping("/{accountNo}/transactions")
////    public List<Transaction> getTransactions(@PathVariable String accountNo) {
////        return transactionService.getTransactionsForAccount(accountNo);
////    }
////    @GetMapping("/list")
////    public List<Account> getAllAccounts() {
////        return accountService.getAllAccountObjects(); // <-- new method
////    }
////
////}
//package com.scb.axcessspringb.controller;
//
//import com.scb.axcessspringb.model.Account;
//import com.scb.axcessspringb.model.Transaction;
//import com.scb.axcessspringb.service.AccountService;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/account")
//public class AccountController {
//
//    private final AccountService accountService;
//
//    public AccountController(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    // Get all accounts
//    @GetMapping("/list")
//    public List<Account> getAllAccounts() {
//        return accountService.getAllAccounts();
//    }
//
//    // Get account balance
//    @GetMapping("/{accountNumber}/balance")
//    public BigDecimal getBalance(@PathVariable String accountNumber) {
//        return accountService.getBalance(accountNumber);
//    }
//
//    // Get transactions for account
//    @GetMapping("/{accountNumber}/transactions")
//    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
//        return accountService.getTransactionsForAccount(accountNumber);
//    }
//}


package com.scb.axcessspringb.controller;

import com.scb.axcessspringb.model.Account;
import com.scb.axcessspringb.model.Transaction;
import com.scb.axcessspringb.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) { this.accountService = accountService; }

    // returns full Account objects (accountNumber, accountName, accountType, balance, currency)
    @GetMapping("/list")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}/balance")
    public BigDecimal getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @GetMapping("/{accountNumber}/transactions")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return accountService.getTransactionsForAccount(accountNumber);
    }
}
