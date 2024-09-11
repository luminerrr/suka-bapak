package com.example.suka_bapak.service.impl;

import com.example.suka_bapak.entity.PatronEntity;
import com.example.suka_bapak.repository.PatronRepository;
import com.example.suka_bapak.service.PatronService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<PatronEntity> getAllPatrons() {
        return patronRepository.findAll();
    }

    public PatronEntity getPatronById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    public PatronEntity savePatron(PatronEntity patron) {
        return patronRepository.save(patron);
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

    @Override
    public Page<PatronEntity> getPatrons(Pageable pageable) {
        return patronRepository.findAll(pageable); }

}
