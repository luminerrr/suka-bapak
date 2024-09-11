package com.example.suka_bapak.repository;


import com.example.suka_bapak.entity.TransactionEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByBook_IdAndPatron_IdAndReturnDateIsNull(Long bookId, Long patronId);
}
