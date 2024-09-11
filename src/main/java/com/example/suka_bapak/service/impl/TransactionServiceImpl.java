package com.example.suka_bapak.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.suka_bapak.constant.GeneralConstant;
import com.example.suka_bapak.dto.request.transactions.CreateTransactionsRequestDto;
import com.example.suka_bapak.dto.response.transactions.CreateTransactionsResponseDto;
import com.example.suka_bapak.dto.response.transactions.ReturnTransactionsResponse;
import com.example.suka_bapak.entity.BookEntity;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.entity.TransactionEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.repository.BookRepository;
import com.example.suka_bapak.repository.PatronRepository;
import com.example.suka_bapak.repository.TransactionRepository;
import com.example.suka_bapak.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Override
    public ResponseEntity<Object> createTransaction(CreateTransactionsRequestDto request) {
        Optional<BookEntity> bookOpt = bookRepository.findById(request.getBookId());
        Optional<PatronEntity> patronOpt = patronRepository.findById(request.getPatronId());
        // Check if book or patron is present
        if ((!bookOpt.isPresent()) || (!patronOpt.isPresent())) {

            throw new ValidationException("Invalid book or patron");
        }
        BookEntity book = bookOpt.get();
        PatronEntity patron = patronOpt.get();

        // TODO: Check if patron could borrow more books

        // Check if book have available copies
        if (book.getAvailable_copies() <= 0) {
            throw new ValidationException("Book is not available");
        }
        TransactionEntity transaction = new TransactionEntity();
        transaction.setPatron(patron);
        transaction.setBook(book);

        LocalDate today = LocalDate.now();
        transaction.setBorrowDate(today);

        LocalDate dueDate = today.plusDays(7);
        transaction.setDueDate(dueDate);

        transaction.setReturnDate(null);
        transaction.setFine(0D);

        transactionRepository.save(transaction);
        CreateTransactionsResponseDto res = new CreateTransactionsResponseDto();
        res.setMessage("Book borrowed successfully");
        res.setDueDate(dueDate);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> returnTransaction(CreateTransactionsRequestDto request) {
        Optional<BookEntity> bookOpt = bookRepository.findById(request.getBookId());
        Optional<PatronEntity> patronOpt = patronRepository.findById(request.getPatronId());
        // Check if book or patron is present
        if ((!bookOpt.isPresent()) || (!patronOpt.isPresent())) {
            throw new ValidationException("Invalid book or patron");
        }
        // Find books with exact book_id and patron_id
        List<TransactionEntity> borrowedBooks = transactionRepository
                .findByBook_IdAndPatron_IdAndReturnDateIsNull(request.getBookId(), request.getPatronId());
        if (borrowedBooks.isEmpty()) {
            throw new ValidationException("Book is not borrowed by this patron");
        }
        // Change returned_date and calculate total fine
        Double totalFine = 0D;
        for (TransactionEntity transaction : borrowedBooks) {
            transaction.setReturnDate(LocalDate.now());
            Long daysOverdue = ChronoUnit.DAYS.between(transaction.getDueDate(), LocalDate.now());
            Double transactionFine = daysOverdue * GeneralConstant.FINE_FEE_PER_DAY;
            transaction.setFine(transactionFine);
            totalFine += transactionFine;
        }

        ReturnTransactionsResponse res = new ReturnTransactionsResponse();
        res.setMessage("Book returned successfully");
        res.setFine(totalFine);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
