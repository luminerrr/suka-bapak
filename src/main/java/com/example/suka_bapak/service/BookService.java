package com.example.suka_bapak.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.suka_bapak.dto.response.books.GetBooksDto;

@Component
public interface BookService {
    ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page);
}
