package com.scb.axcessspringb.service;

import com.scb.axcessspringb.model.Account;
import com.scb.axcessspringb.repository.AccountRepository;
import com.scb.axcessspringb.repository.BatchRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountSyncService {

    private final AccountRepository accountRepository;
    private final BatchRepository batchRepository;

    public AccountSyncService(AccountRepository accountRepository, BatchRepository batchRepository) {
        this.accountRepository = accountRepository;
        this.batchRepository = batchRepository;
    }

    @PostConstruct
    public void syncAccountsFromBatches() {
        List<String> debitAccounts = batchRepository.findDistinctByDebitAccountIsNotNull();
        for (String acc : debitAccounts) {
            if (!accountRepository.existsById(acc)) {
                Account a = new Account();
                a.setAccountNumber(acc);
                a.setAccountName("AutoSynced-" + acc);
                a.setAccountType("Current");
                a.setBalance(BigDecimal.ZERO);
                a.setCurrency("INR");
                accountRepository.save(a);
            }
        }
    }
}
