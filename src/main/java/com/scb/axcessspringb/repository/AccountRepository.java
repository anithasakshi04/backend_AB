package com.scb.axcessspringb.repository;

import com.scb.axcessspringb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    // no extra methods needed for now
}
