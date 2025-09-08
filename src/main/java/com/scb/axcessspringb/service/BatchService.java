package com.scb.axcessspringb.service;

import com.scb.axcessspringb.dto.BatchDTO;
import com.scb.axcessspringb.dto.PayrollEntryDTO;
import com.scb.axcessspringb.model.Batch;
import com.scb.axcessspringb.model.PayrollEntry;
import com.scb.axcessspringb.repository.BatchRepository;
import com.scb.axcessspringb.repository.PayrollEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    @Autowired private BatchRepository batchRepository;
    @Autowired private PayrollEntryRepository payrollEntryRepository;

    public List<BatchDTO> getAllBatches() {
        return batchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BatchDTO getBatchById(String id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return convertToDTO(batch);
    }

    public BatchDTO createBatch(BatchDTO batchDTO) {
        Batch batch = convertToEntity(batchDTO);
        batch.setTotalAmount(BigDecimal.ZERO);
        batchRepository.save(batch);
        return convertToDTO(batch);
    }

    public BatchDTO updateBatch(String id, BatchDTO batchDTO) {
        Batch existing = batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        existing.setPaymentDate(batchDTO.getPaymentDate());
        existing.setPaymentType(batchDTO.getPaymentType());
        existing.setDebitAccount(batchDTO.getDebitAccount());
        existing.setAccountType(batchDTO.getAccountType());
        existing.setStatus(batchDTO.getStatus());

        batchRepository.save(existing);
        return convertToDTO(existing);
    }

    public void deleteBatch(String id) {
        batchRepository.deleteById(id);
    }

    private BatchDTO convertToDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setId(batch.getId());
        dto.setName(batch.getName());
        dto.setStatus(batch.getStatus());
        dto.setTotalAmount(batch.getTotalAmount());
        dto.setPaymentDate(batch.getPaymentDate());
        dto.setPaymentType(batch.getPaymentType());
        dto.setCurrency(batch.getCurrency());
        dto.setDebitAccount(batch.getDebitAccount());
        dto.setAccountType(batch.getAccountType());

        // Convert entries
        if (batch.getEntries() != null) {
            dto.setEntries(batch.getEntries().stream()
                    .map(this::convertToEntryDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private PayrollEntryDTO convertToEntryDTO(PayrollEntry entry) {
        PayrollEntryDTO dto = new PayrollEntryDTO();
        dto.setId(entry.getId());
        dto.setMethod(entry.getMethod());
        dto.setPayeeDetails(entry.getPayeeDetails());
        dto.setPayeeName(entry.getPayeeName());
        dto.setBankDetails(entry.getBankDetails());
        dto.setYourReference(entry.getYourReference());
        dto.setPaymentReference(entry.getPaymentReference());
        dto.setAmount(entry.getAmount());
        dto.setNotes(entry.getNotes());
        return dto;
    }

    private Batch convertToEntity(BatchDTO dto) {
        Batch batch = new Batch();
        batch.setId(dto.getId());
        batch.setName(dto.getName());
        batch.setStatus(dto.getStatus());
        batch.setPaymentDate(dto.getPaymentDate());
        batch.setPaymentType(dto.getPaymentType());
        batch.setDebitAccount(dto.getDebitAccount());
        batch.setAccountType(dto.getAccountType());
        return batch;
    }
}