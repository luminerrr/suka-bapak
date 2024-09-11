package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.constant.GeneralConstant;
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
import com.example.suka_bapak.dto.response.books.GetOverdueBooksResponseDto;
import com.example.suka_bapak.entity.BookEntity;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.entity.TransactionEntity;
import com.example.suka_bapak.mapper.BookMapper;
import com.example.suka_bapak.repository.BookRepository;
import com.example.suka_bapak.repository.PatronRepository;
import com.example.suka_bapak.service.BookService;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PatronRepository patronRepository;


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
        return new ResponseEntity<>(Map.of(
                "id", saved.getId(),
                "title", saved.getTitle(),
                "available_copies", saved.getAvailable_copies()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateBook(Long id, CreateBookRequest createBookRequest) {
        BookEntity existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setTitle(createBookRequest.getTitle());
        existingBook.setAuthor(createBookRequest.getAuthor());
        existingBook.setQuantity(createBookRequest.getQuantity());
        existingBook.setUpdated_at(LocalDate.now());

        try {
            bookRepository.save(existingBook);
            return new ResponseEntity<>(Map.of("message", "Book details updated successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @Override
    public ResponseEntity<List<GetOverdueBooksResponseDto>> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<TransactionEntity> overdueTransactions = transactionRepository.findByDueDateBefore(today);
        List<GetOverdueBooksResponseDto> response = new ArrayList<>();
        for (TransactionEntity transaction : overdueTransactions) {
            GetOverdueBooksResponseDto dto = new GetOverdueBooksResponseDto();
            dto.setBookTitle(transaction.getBook().getTitle());
            dto.setPatronName(transaction.getPatron().getName());
            dto.setDueDate(transaction.getDueDate());
            Long overdue = ChronoUnit.DAYS.between(transaction.getDueDate(), LocalDate.now());
            overdue = overdue > 0 ? overdue : 0;
            dto.setDaysOverdue(overdue.intValue());
            Double fine = overdue * GeneralConstant.FINE_FEE_PER_DAY;
            dto.setFine(fine);
            response.add(dto);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
