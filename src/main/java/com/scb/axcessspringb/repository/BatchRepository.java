package com.scb.axcessspringb.repository;

import com.scb.axcessspringb.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, String> {

    List<Batch> findByDebitAccount(String debitAccount);

    @Query("SELECT DISTINCT b.debitAccount FROM Batch b WHERE b.debitAccount IS NOT NULL")
    List<String> findDistinctByDebitAccountIsNotNull();
}
