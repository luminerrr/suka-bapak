package com.example.suka_bapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page) {
        return bookService.getAllBooks(page);
    }
    
}
