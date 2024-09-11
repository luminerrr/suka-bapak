package com.example.suka_bapak.service.impl;


import com.example.suka_bapak.repository.TransactionRepository;
import com.example.suka_bapak.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


}
