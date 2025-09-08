package com.scb.axcessspringb.config;

import com.scb.axcessspringb.model.Batch;
import com.scb.axcessspringb.model.PayrollEntry;
import com.scb.axcessspringb.repository.BatchRepository;
import com.scb.axcessspringb.repository.PayrollEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PayrollEntryRepository payrollEntryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (batchRepository.count() == 0) {
            // Create sample batches
            Batch batch1 = new Batch("E000021", "April Salary 2025", "draft", "Domestic", "1234567890", "Savings");
            Batch batch2 = new Batch("E000022", "May Salary 2025", "draft", "Domestic", "2345678901", "Current");

            batchRepository.save(batch1);
            batchRepository.save(batch2);

            // Create sample payroll entries for batch1
            PayrollEntry entry1 = new PayrollEntry(batch1, "John Doe", new BigDecimal("25000"));
            PayrollEntry entry2 = new PayrollEntry(batch1, "Jane Smith", new BigDecimal("30000"));

            payrollEntryRepository.save(entry1);
            payrollEntryRepository.save(entry2);

            System.out.println("Sample data initialized successfully!");
        }
    }
}