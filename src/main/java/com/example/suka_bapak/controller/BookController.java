package com.example.suka_bapak.controller;

import com.example.suka_bapak.dto.request.books.CreateBookRequest;
import com.example.suka_bapak.entity.BookEntity;
import com.example.suka_bapak.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.service.BookService;

import java.util.Map;


@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page) {
        return bookService.getAllBooks(page);
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long book_id){
        BookEntity book = bookService.getBookById(book_id);
        if(book!=null){
            return ResponseEntity.ok(book);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody CreateBookRequest createBookRequest) {
        try {
            BookEntity newBook = bookService.createBook(createBookRequest);
            return new ResponseEntity<>(Map.of(
                    "id", newBook.getId(),
                    "title", newBook.getTitle(),
                    "available_copies", newBook.getAvailable_copies()
            ), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{book_id}")
    public ResponseEntity<?> updateBook(
            @PathVariable("book_id") Long bookId,
            @RequestBody CreateBookRequest createBookRequest) {
        try {
            bookService.updateBook(bookId, createBookRequest);
            return new ResponseEntity<>(Map.of("message", "Book details updated successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Book not found."), HttpStatus.NOT_FOUND);
        }
    }



}
