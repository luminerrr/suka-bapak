package com.example.suka_bapak.repository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suka_bapak.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findAll(Pageable page);
}
