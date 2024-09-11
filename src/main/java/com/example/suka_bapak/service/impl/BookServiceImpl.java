package com.example.suka_bapak.service.impl;

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
public class BookServiceImpl  implements BookService {
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
}
