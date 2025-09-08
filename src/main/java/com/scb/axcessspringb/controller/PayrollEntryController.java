package com.scb.axcessspringb.controller;
import com.scb.axcessspringb.dto.PayrollEntryDTO;
import com.scb.axcessspringb.model.PayrollEntry;
import com.scb.axcessspringb.service.PayrollEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:3000")
public class PayrollEntryController {

    @Autowired private PayrollEntryService payrollService;

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<PayrollEntryDTO>> getByBatch(@PathVariable String batchId) {
        List<PayrollEntry> entries = payrollService.getByBatch(batchId);
        List<PayrollEntryDTO> dtos = entries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/batch/{batchId}")
    public ResponseEntity<PayrollEntryDTO> addEntry(@PathVariable String batchId, @RequestBody PayrollEntryDTO dto) {
        PayrollEntry entry = new PayrollEntry();
        entry.setMethod(dto.getMethod());
        entry.setPayeeDetails(dto.getPayeeDetails());
        entry.setPayeeName(dto.getPayeeName());
        entry.setBankDetails(dto.getBankDetails());
        entry.setYourReference(dto.getYourReference());
        entry.setPaymentReference(dto.getPaymentReference());
        entry.setAmount(dto.getAmount() != null ? dto.getAmount() : BigDecimal.ZERO);
        entry.setNotes(dto.getNotes());

        PayrollEntry saved = payrollService.createEntry(batchId, entry);

        return ResponseEntity.ok(convertToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollEntryDTO> updateEntry(@PathVariable Long id, @RequestBody PayrollEntryDTO dto) {
        PayrollEntry entry = new PayrollEntry();
        entry.setMethod(dto.getMethod());
        entry.setPayeeDetails(dto.getPayeeDetails());
        entry.setPayeeName(dto.getPayeeName());
        entry.setBankDetails(dto.getBankDetails());
        entry.setYourReference(dto.getYourReference());
        entry.setPaymentReference(dto.getPaymentReference());
        entry.setAmount(dto.getAmount() != null ? dto.getAmount() : BigDecimal.ZERO);
        entry.setNotes(dto.getNotes());

        PayrollEntry updated = payrollService.updateEntry(id, entry);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        payrollService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    private PayrollEntryDTO convertToDTO(PayrollEntry entry) {
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
}