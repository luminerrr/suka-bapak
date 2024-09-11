package com.example.suka_bapak.repository;

import com.example.suka_bapak.entity.PatronEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<PatronEntity, Long> {

    Page<PatronEntity> findAll(Pageable pageable);
}
