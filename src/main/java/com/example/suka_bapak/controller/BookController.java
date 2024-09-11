package com.example.suka_bapak.controller;

import com.example.suka_bapak.dto.request.books.CreateBookRequest;
import com.example.suka_bapak.entity.BookEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.service.BookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public ResponseEntity<Object> addBook(@RequestBody CreateBookRequest createBookRequest) {
        
        return bookService.createBook(createBookRequest);
    }

    @PutMapping("/{book_id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable("book_id") Long bookId,
            @RequestBody CreateBookRequest createBookRequest) {
                return bookService.updateBook(bookId, createBookRequest);
    }

        @Autowired
        private BookRepository bookRepository;

        @GetMapping("/search")
        public ResponseEntity<?> searchBooks(
                @RequestParam(required = false) String title,
                @RequestParam(required = false) String author)
        {
            if (title == null && author == null) {
                return ResponseEntity.badRequest().body("Please provide either title or author for search.");
            }

            List<BookEntity> books = bookRepository.findByTitleOrAuthor(title, author);

            List<Map<String, Object>> response = books.stream().map(book -> {
                Map<String, Object> result = new HashMap<>();
                result.put("id", book.getId());
                result.put("title", book.getTitle());
                result.put("author", book.getAuthor());
                result.put("available_copies", book.getAvailable_copies());
                return result;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        }
    }

