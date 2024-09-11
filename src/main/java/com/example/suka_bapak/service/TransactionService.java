package com.example.suka_bapak.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.suka_bapak.dto.request.transactions.CreateTransactionsRequestDto;

@Component
public interface TransactionService {
    ResponseEntity<Object>  createTransaction(CreateTransactionsRequestDto request);

}
