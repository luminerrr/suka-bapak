package com.example.suka_bapak.controller;

import com.example.suka_bapak.dto.request.CreateBookRequest;
import com.example.suka_bapak.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.service.BookService;


@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page) {
        return bookService.getAllBooks(page);
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@RequestBody CreateBookRequest book){
        BookEntity savedBook = bookService.createBook(book);
        return ResponseEntity.ok(savedBook);
    }



}
