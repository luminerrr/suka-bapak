package com.example.suka_bapak.service;

import com.example.suka_bapak.entity.PatronEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatronService {

    List<PatronEntity> getAllPatrons();

    PatronEntity getPatronById (Long id);

    PatronEntity savePatron (PatronEntity patron);

    void deletePatron(Long id);

    Page<PatronEntity> getPatrons(Pageable pageable);

}
