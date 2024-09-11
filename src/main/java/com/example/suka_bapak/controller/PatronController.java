package com.example.suka_bapak.controller;

import com.example.suka_bapak.dto.request.patrons.CreatePatronRequest;
import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    // Get all patrons
    @GetMapping
    public ResponseEntity<Page<GetPatronDto>> getAllPatrons(Pageable page) {
        return patronService.getAllPatrons(page);
    }

    // Get patron by a certain id
    @GetMapping("/{patron_id}")
    public ResponseEntity<PatronEntity> getPatronById(
            @PathVariable Long id) {
        PatronEntity patron = patronService.getPatronById(id);
        if (patron != null) {
            return ResponseEntity.ok(patron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create new patron
    @PostMapping
    public ResponseEntity<PatronEntity> createPatron(
            @RequestBody CreatePatronRequest patron) {
        PatronEntity savedPatron = patronService.createPatron(patron);
        return ResponseEntity.ok(savedPatron);
    }

    // Update patron
    @PutMapping("/{patron_id}")
    public ResponseEntity<Object> updatePatron(
            @PathVariable("patron_id") Long id,
            @RequestBody CreatePatronRequest createPatronRequest) {
        return patronService.updatePatron(id, createPatronRequest);
    }

}
