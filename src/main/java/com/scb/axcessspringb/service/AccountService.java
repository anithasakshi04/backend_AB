

package com.scb.axcessspringb.service;
import com.scb.axcessspringb.exception.ResourceNotFoundException;
import com.scb.axcessspringb.model.Account;
import com.scb.axcessspringb.model.Batch;
import com.scb.axcessspringb.model.Transaction;
import com.scb.axcessspringb.repository.AccountRepository;
import com.scb.axcessspringb.repository.BatchRepository;
import com.scb.axcessspringb.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

//@Service
//public class AccountService {
//
//    private final AccountRepository accountRepository;
//    private final BatchRepository batchRepository;
//    private final TransactionRepository transactionRepository;
//
//    public AccountService(AccountRepository accountRepository,
//                          BatchRepository batchRepository,
//                          TransactionRepository transactionRepository) {
//        this.accountRepository = accountRepository;
//        this.batchRepository = batchRepository;
//        this.transactionRepository = transactionRepository;
//    }
//
//    // 1. Get All Accounts
//    public List<Account> getAllAccounts() {
//        List<Account> accounts = accountRepository.findAll();
//
//        // also include debit accounts from Batch if not present
//        List<String> batchAccounts = batchRepository.findDistinctByDebitAccountIsNotNull();
//
//        for (String accNo : batchAccounts) {
//            boolean exists = accounts.stream()
//                    .anyMatch(a -> a.getAccountNumber().equals(accNo));
//            if (!exists) {
//                accounts.add(new Account(
//                        accNo,
//                        "AutoSynced-" + accNo,
//                        "Current",
//                        BigDecimal.valueOf(100000),
//                        "INR"
//                ));
//            }
//        }
//        return accounts;
//    }
//
//    // 2. Get Balance
//    public BigDecimal getBalance(String accountNo) {
//        Account account = accountRepository.findById(accountNo)
//                .orElseThrow(() -> new RuntimeException("Account not found"));
//
//        BigDecimal openingBalance = account.getBalance();
//
//        List<Transaction> transactions = transactionRepository.findByAccount_AccountNumber(accountNo);
//
//        BigDecimal net = transactions.stream()
//                .map(t -> "DEBIT".equalsIgnoreCase(t.getType())
//                        ? t.getAmount().negate()
//                        : t.getAmount())
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return openingBalance.add(net);
//    }
//
//    // 3. Get Transactions (link to Batches & Payments)
//    public List<Transaction> getTransactionsForAccount(String accountNo) {
//        // find all batches where this account was used
//        List<Batch> batches = batchRepository.findByDebitAccount(accountNo);
//
//        // collect transactions linked to those batches
//        return batches.stream()
//                .flatMap(batch -> transactionRepository.findByBatch(batch).stream())
//                .toList();
//    }
//}
//

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BatchRepository batchRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository,
                          BatchRepository batchRepository,
                          TransactionRepository transactionRepository,
                          TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.batchRepository = batchRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    /**
     * Return full Account objects merged from accounts table + any debitAccount values in batches.
     * Accounts that exist only in batches are returned as in-memory Account objects (not saved) unless
     * you run AccountSyncService to persist them.
     */
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        List<String> batchAccounts = batchRepository.findDistinctByDebitAccountIsNotNull();
        for (String accNo : batchAccounts) {
            boolean exists = accounts.stream().anyMatch(a -> a.getAccountNumber().equals(accNo));
            if (!exists) {
                Account a = new Account();
                a.setAccountNumber(accNo);
                a.setAccountName("AutoSynced-" + accNo);
                a.setAccountType("Current");
                a.setBalance(BigDecimal.ZERO); // default if not persisted
                a.setCurrency("INR");
                accounts.add(a);
            }
        }
        return accounts;
    }

    /**
     * Calculate current balance for accountNumber:
     * account.balance (from accounts table) + net(transactions)
     * (DEBIT subtracts, CREDIT adds). Transactions include those attached directly to account.
     */
    public BigDecimal getBalance(String accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountNumber));

        BigDecimal opening = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

        // transactions directly associated with account
        List<Transaction> accountTxns = transactionRepository.findByAccount_AccountNumber(accountNumber);

        BigDecimal net = accountTxns.stream()
                .map(t -> "DEBIT".equalsIgnoreCase(t.getType()) ? t.getAmount().negate() : t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Also include transactions from batches where debitAccount == accountNumber
        List<Batch> batches = batchRepository.findByDebitAccount(accountNumber);
        BigDecimal batchTxnSum = batches.stream()
                .flatMap(b -> transactionRepository.findByBatch(b).stream())
                .map(t -> "DEBIT".equalsIgnoreCase(t.getType()) ? t.getAmount().negate() : t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalNet = net.add(batchTxnSum);

        return opening.add(totalNet);
    }

    /**
     * Return list of transactions for an account by:
     *  - transactions directly linked to the account
     *  - transactions linked to batches where debitAccount == accountNumber
     */
    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        List<Transaction> direct = transactionRepository.findByAccount_AccountNumber(accountNumber);

        List<Batch> batches = batchRepository.findByDebitAccount(accountNumber);
        List<Transaction> fromBatches = batches.stream()
                .flatMap(b -> transactionRepository.findByBatch(b).stream())
                .collect(Collectors.toList());

        // Merge unique (by id)
        Map<String, Transaction> map = new LinkedHashMap<>();
        for (Transaction t : direct) map.put(t.getId(), t);
        for (Transaction t : fromBatches) map.put(t.getId(), t);

        return new ArrayList<>(map.values());
    }
}