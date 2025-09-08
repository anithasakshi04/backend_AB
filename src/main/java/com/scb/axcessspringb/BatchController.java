package com.scb.axcessspringb;

import com.scb.axcessspringb.dto.BatchDTO;
import com.scb.axcessspringb.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin(origins = "http://localhost:3000")
public class BatchController {

    @Autowired private BatchService batchService;

    @GetMapping
    public ResponseEntity<List<BatchDTO>> getAllBatches() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable String id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    @PostMapping
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        return ResponseEntity.ok(batchService.createBatch(batchDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchDTO> updateBatch(@PathVariable String id, @RequestBody BatchDTO batchDTO) {
        return ResponseEntity.ok(batchService.updateBatch(id, batchDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable String id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }
}