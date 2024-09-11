package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.dto.request.patrons.CreatePatronRequest;
import com.example.suka_bapak.dto.response.patrons.GetOngoingBorrowResponseDto;
import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.dto.response.patrons.GetPatronTransactionHistoryResponseDto;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.entity.TransactionEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.mapper.PatronMapper;
import com.example.suka_bapak.repository.PatronRepository;
import com.example.suka_bapak.repository.TransactionRepository;
import com.example.suka_bapak.service.PatronService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PatronMapper patronMapper;

    @Override
    public PatronEntity getPatronById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<Page<GetPatronDto>> getAllPatrons(Pageable page) {
        Page<PatronEntity> patronPageEntity = patronRepository.findAll(page);
        Page<GetPatronDto> patronPageDto = patronPageEntity.map(patronMapper::toDto);

        return new ResponseEntity<>(patronPageDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createPatron(CreatePatronRequest createPatronRequest) {
        validatePatronRequest(createPatronRequest);

        PatronEntity patron = new PatronEntity();
        patron.setName(createPatronRequest.getName());
        patron.setEmail(createPatronRequest.getEmail());
        patron.setMembership_type(createPatronRequest.getMembership_type());

        PatronEntity saved = patronRepository.save(patron);
        try {
            return new ResponseEntity<>(Map.of(
                    "id", saved.getId(),
                    "name", saved.getName(),
                    "email", saved.getEmail(),
                    "membership_type", saved.getMembership_type()), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> updatePatron(Long id, CreatePatronRequest createPatronRequest) {
        PatronEntity existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));
        existingPatron.setName(createPatronRequest.getName());
        existingPatron.setEmail(createPatronRequest.getEmail());
        existingPatron.setMembership_type(createPatronRequest.getMembership_type());
        try {
            patronRepository.save(existingPatron);
            return new ResponseEntity<>(Map.of("message", "Patron details updated successfully."), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Patron not found."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deletePatron(Long id) {
        PatronEntity existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));

//        validatePatronLoan(id);

        patronRepository.deleteById(id);
    }

    private void validatePatronRequest(CreatePatronRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Invalid email or missing required fields.");
        }
        String email = request.getEmail();
        if (email == null || !email.contains("@")) {
            throw new ValidationException("Invalid email. Email must contain '@'.");
        }
        if (patronRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email must be unique.");
        }
        String membershipType = request.getMembership_type();
        if (membershipType == null || (!"regular".equals(membershipType) && !"premium".equals(membershipType))) {
            throw new ValidationException("Membership type must be either regular or premium.");
        }
    }


    private void validatePatronLoan(Long id) {
        List<TransactionEntity> hasActiveLoans = transactionRepository.findByPatron_IdAndReturnDateIsNull(id);
        if (!hasActiveLoans.isEmpty()) {
            throw new ValidationException("Cannot delete patron with active loans.");
        }
    }

    @Override
    public ResponseEntity<List<GetPatronTransactionHistoryResponseDto>> getTransactionHistory(Long id) {
        List<TransactionEntity> transactions = transactionRepository.findByPatron_IdAndReturnDateIsNotNull(id);
        List<GetPatronTransactionHistoryResponseDto> response = new ArrayList<>();
        // Map from transaction entity to response dto
        for (TransactionEntity transaction : transactions) {
            GetPatronTransactionHistoryResponseDto dto = new GetPatronTransactionHistoryResponseDto();
            dto.setBookTitle(transaction.getBook().getTitle());
            dto.setBorrowDate(transaction.getBorrowDate());
            dto.setReturneDate(transaction.getBorrowDate());
            dto.setFine(transaction.getFine());
            response.add(dto);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GetOngoingBorrowResponseDto>> getOngoingBorrows(Long id) {
        List<TransactionEntity> transactions = transactionRepository.findByPatron_IdAndReturnDateIsNull(id);
        List<GetOngoingBorrowResponseDto> response = new ArrayList<>();
        // Map TransactionEntity to ResponseDto
        for (TransactionEntity transaction : transactions) {
            GetOngoingBorrowResponseDto dto = new GetOngoingBorrowResponseDto();
            dto.setBookTitle(transaction.getBook().getTitle());
            dto.setBorrowDate(transaction.getBorrowDate());
            dto.setDueDate(transaction.getDueDate());
            response.add(dto);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}