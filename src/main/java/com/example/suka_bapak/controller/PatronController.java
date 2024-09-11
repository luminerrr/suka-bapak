package com.example.suka_bapak.controller;

import com.example.suka_bapak.dto.request.patrons.CreatePatronRequest;
import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.dto.response.patrons.GetPatronTransactionHistoryResponseDto;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

//    Get all patrons
    @GetMapping
    public ResponseEntity<Page<GetPatronDto>> getAllPatrons(Pageable page) {
        return patronService.getAllPatrons(page);
    }

    //    Get patron by a certain id
    @GetMapping("/{patron_id}")
    public ResponseEntity<PatronEntity> getPatronById(
            @PathVariable Long patron_id
    ){
        PatronEntity patron = patronService.getPatronById(patron_id);
        if (patron != null) {
            return ResponseEntity.ok(patron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    Create new patron
    @PostMapping
    public ResponseEntity<Object> addPatron(
            @RequestBody CreatePatronRequest createPatronRequest
    ){
        return patronService.createPatron(createPatronRequest);
    }

//    Update patron
    @PutMapping("/{patron_id}")
    public ResponseEntity<?> updatePatron(
            @PathVariable ("patron_id") Long id,
            @RequestBody CreatePatronRequest createPatronRequest) {
        try {
            patronService.updatePatron(id, createPatronRequest);
            return new ResponseEntity<>(Map.of("message", "Patron details updated successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Patron not found."), HttpStatus.NOT_FOUND);
        }
    }


//    Delete patron
    @DeleteMapping("/{patron_id}")
    public ResponseEntity<?> deletePatron(
            @PathVariable("patron_id") Long id
            ) {
        try {
            patronService.deletePatron(id);
            return new ResponseEntity<>(Map.of("message", "Patron deleted successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Patron not found."), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{patron_id}/borrow_history")
    public ResponseEntity<List<GetPatronTransactionHistoryResponseDto>> getTransactionHistory(
        @PathVariable("patron_id") Long id
        ) {
        return patronService.getTransactionHistory(id);
    }
    
}
