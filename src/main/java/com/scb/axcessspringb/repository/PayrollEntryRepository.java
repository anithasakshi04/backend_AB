package com.scb.axcessspringb.repository;

import com.scb.axcessspringb.model.PayrollEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollEntryRepository extends JpaRepository<PayrollEntry, Long> {
    List<PayrollEntry> findByBatchId(String batchId);
}