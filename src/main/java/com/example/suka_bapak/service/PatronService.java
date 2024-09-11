package com.example.suka_bapak.service;

import com.example.suka_bapak.dto.request.patrons.CreatePatronRequest;
import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.entity.PatronEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface PatronService {

    ResponseEntity<Page<GetPatronDto>> getAllPatrons(Pageable page);

    PatronEntity getPatronById (Long id);

    ResponseEntity<Object> createPatron (CreatePatronRequest createPatronRequest);

    ResponseEntity<Object> updatePatron (Long id, CreatePatronRequest createPatronRequest);

    void deletePatron (Long id);

}

