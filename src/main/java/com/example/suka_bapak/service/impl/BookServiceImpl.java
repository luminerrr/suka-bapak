package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.dto.request.books.CreateBookRequest;
import com.example.suka_bapak.exception.ValidationException;
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

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;


    @Override
    public ResponseEntity<Page<GetBooksDto>> getAllBooks(Pageable page) {
        Page<BookEntity> bookPageEntity = bookRepository.findAll(page);
        Page<GetBooksDto> bookPageDto = bookPageEntity.map(bookMapper::toDto);

        return new ResponseEntity<>(bookPageDto, HttpStatus.OK);
    }

    @Override
    public BookEntity createBook(CreateBookRequest createBookRequest) {
        validateBookRequest(createBookRequest);

        BookEntity book = new BookEntity();
        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setIsbn(createBookRequest.getIsbn());
        book.setQuantity(createBookRequest.getQuantity());
        book.setAvailable_copies(createBookRequest.getQuantity());

        return bookRepository.save(book);
    }

    @Override
    public BookEntity updateBook(Long id, CreateBookRequest createBookRequest) {
        BookEntity existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(createBookRequest.getTitle());
        existingBook.setIsbn(createBookRequest.getIsbn());
        existingBook.setAuthor(createBookRequest.getAuthor());
        existingBook.setQuantity(createBookRequest.getQuantity());

        return bookRepository.save(existingBook);
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
}
