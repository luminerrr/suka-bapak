package com.example.suka_bapak.controller;

import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

//    Get all patrons
    @GetMapping()
    public List<PatronEntity> getAllPatrons() {
        return patronService.getAllPatrons();
    }

//    Get patron by a certain id
    @GetMapping("/{id}")
    public ResponseEntity<PatronEntity> getPatronById(
            @PathVariable Long id
    ){
        PatronEntity patron = patronService.getPatronById(id);
        if (patron != null) {
            return ResponseEntity.ok(patron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
