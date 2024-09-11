package com.example.suka_bapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.suka_bapak.dto.request.transactions.CreateTransactionsRequestDto;
import com.example.suka_bapak.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/borrow")
    public ResponseEntity<Object> createTransaction(@RequestBody CreateTransactionsRequestDto request) {
        return transactionService.createTransaction(request);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTransaction(@RequestBody CreateTransactionsRequestDto request) {
        return transactionService.returnTransaction(request);
    }
    

    
}
