package com.scb.axcessspringb.service;

import com.scb.axcessspringb.model.Batch;
import com.scb.axcessspringb.model.PayrollEntry;
import com.scb.axcessspringb.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PayrollEntryService {

    @Autowired private com.scb.axcessspringb.repository.PayrollEntryRepository payrollRepo;
    @Autowired private BatchRepository batchRepo;

    public List<PayrollEntry> getByBatch(String batchId) {
        return payrollRepo.findByBatchId(batchId);
    }

    public PayrollEntry createEntry(String batchId, PayrollEntry entry) {
        Batch batch = batchRepo.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        entry.setBatch(batch);
        PayrollEntry saved = payrollRepo.save(entry);

        // Update batch total
        BigDecimal total = payrollRepo.findByBatchId(batchId).stream()
                .map(PayrollEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        batch.setTotalAmount(total);
        batchRepo.save(batch);

        return saved;
    }

    public PayrollEntry updateEntry(Long id, PayrollEntry entry) {
        PayrollEntry existing = payrollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        existing.setMethod(entry.getMethod());
        existing.setPayeeDetails(entry.getPayeeDetails());
        existing.setPayeeName(entry.getPayeeName());
        existing.setBankDetails(entry.getBankDetails());
        existing.setYourReference(entry.getYourReference());
        existing.setPaymentReference(entry.getPaymentReference());
        existing.setAmount(entry.getAmount() != null ? entry.getAmount() : BigDecimal.ZERO);
        existing.setNotes(entry.getNotes());

        PayrollEntry updated = payrollRepo.save(existing);

        // Update batch total
        String batchId = updated.getBatch().getId();
        BigDecimal total = payrollRepo.findByBatchId(batchId).stream()
                .map(PayrollEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        updated.getBatch().setTotalAmount(total);
        batchRepo.save(updated.getBatch());

        return updated;
    }

    public void deleteEntry(Long id) {
        PayrollEntry entry = payrollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        String batchId = entry.getBatch().getId();
        payrollRepo.delete(entry);

        // Recalculate total
        BigDecimal total = payrollRepo.findByBatchId(batchId).stream()
                .map(PayrollEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Batch batch = batchRepo.findById(batchId).orElse(null);
        if (batch != null) {
            batch.setTotalAmount(total);
            batchRepo.save(batch);
        }
    }
}