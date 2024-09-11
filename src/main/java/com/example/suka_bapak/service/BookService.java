package com.example.suka_bapak.service;

import com.example.suka_bapak.dto.request.books.CreateBookRequest;
import com.example.suka_bapak.entity.BookEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.dto.response.books.GetOverdueBooksResponseDto;

@Component
public interface BookService {
    ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page);

    ResponseEntity<Object> createBook(CreateBookRequest createBookRequest);

    ResponseEntity<Object> updateBook(Long id, CreateBookRequest createBookRequest);

    BookEntity getBookById(Long id);

    ResponseEntity<Object> checkBookAvailability(Long book_id);

    void deleteBook(Long bookId);

    ResponseEntity<List<GetOverdueBooksResponseDto>> getOverdueBooks();
}




