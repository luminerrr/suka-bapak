package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.dto.request.patrons.CreatePatronRequest;
import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.exception.ValidationException;
import com.example.suka_bapak.mapper.PatronMapper;
import com.example.suka_bapak.repository.PatronRepository;
import com.example.suka_bapak.service.PatronService;
import lombok.extern.slf4j.Slf4j;
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
    public PatronEntity createPatron(CreatePatronRequest createPatronRequest) {
        validatePatronRequest(createPatronRequest);

        PatronEntity patron = new PatronEntity();
        patron.setName(createPatronRequest.getName());
        patron.setEmail(createPatronRequest.getEmail());
        patron.setMembership_type(createPatronRequest.getMembership_type());

        return patronRepository.save(patron);
    }

    @Override
    public PatronEntity updatePatron(Long id, CreatePatronRequest createPatronRequest) {
        PatronEntity existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));
        existingPatron.setName(createPatronRequest.getName());
        existingPatron.setEmail(createPatronRequest.getEmail());
        existingPatron.setMembership_type(createPatronRequest.getMembership_type());

        return patronRepository.save(existingPatron);
    }

    @Override
    public void deletePatron(Long id, CreatePatronRequest createPatronRequest) {
        PatronEntity existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));

        patronRepository.deleteById(id);
    }

    private void validatePatronRequest(CreatePatronRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty())
        {
            throw new ValidationException("Invalid email or missing required fields.");
        }
        if (patronRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email must be unique.");
        }
        if (request.getMembership_type() != "regular" || request.getMembership_type() != "premium") {
            throw new ValidationException("Membership type must be either regular or premium.");
        }
    }

}