package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.dto.request.books.CreateBookRequest;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.entity.BookEntity;
import com.example.suka_bapak.mapper.BookMapper;
import com.example.suka_bapak.repository.BookRepository;
import com.example.suka_bapak.service.BookService;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page) {
        Page<BookEntity> bookPageEntity = bookRepository.findAll(page);
        Page<GetBooksDto> bookPageDto = bookPageEntity.map(bookMapper::toDto);

        return new ResponseEntity<>(bookPageDto, HttpStatus.OK);
    }

    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<Object> createBook(CreateBookRequest createBookRequest) {
        validateBookRequest(createBookRequest);

        LocalDateTime timeNow = LocalDateTime.now();

        BookEntity book = new BookEntity();
        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setIsbn(createBookRequest.getIsbn());
        book.setQuantity(createBookRequest.getQuantity());
        book.setAvailable_copies(createBookRequest.getQuantity());
        book.setCreated_at(LocalDate.from(timeNow));
        book.setUpdated_at(LocalDate.from(timeNow));

        BookEntity saved = bookRepository.save(book);
        try {
            return new ResponseEntity<>(Map.of(
                    "id", saved.getId(),
                    "title", saved.getTitle(),
                    "available_copies", saved.getAvailable_copies()), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> updateBook(Long id, CreateBookRequest createBookRequest) {
        LocalDateTime timeNow = LocalDateTime.now();
        BookEntity existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(createBookRequest.getTitle());
        existingBook.setIsbn(createBookRequest.getIsbn());
        existingBook.setAuthor(createBookRequest.getAuthor());
        existingBook.setQuantity(createBookRequest.getQuantity());
        existingBook.setCreated_at(LocalDate.from(timeNow));
        existingBook.setUpdated_at(LocalDate.from(timeNow));

        try {
            bookRepository.save(existingBook);
            return new ResponseEntity<>(Map.of("message", "Book details updated successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Book not found."), HttpStatus.NOT_FOUND);
        }
    }

    private void validateBookRequest(CreateBookRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty() ||
                request.getAuthor() == null || request.getAuthor().trim().isEmpty() ||
                request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
            throw new ValidationException("Invalid input. Ensure all fields are filled and ISBN is unique.");
        }
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new ValidationException("ISBN must be unique.");
        }
        if (request.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be a positive integer.");
        }
    }

    public void deleteBook(Long bookId) throws ValidationException {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        boolean hasActiveLoans = transactionRepository.existsByBookIdAndReturnDateIsNull(bookId);
        if (hasActiveLoans) {
            throw new ValidationException("Cannot delete book with active loans.");
        }

        bookRepository.delete(book);
    }

}
