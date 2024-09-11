package com.example.suka_bapak.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suka_bapak.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    
}
