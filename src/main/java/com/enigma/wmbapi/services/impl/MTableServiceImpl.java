package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.entity.MTable;
import com.enigma.wmbapi.repository.MTableRepository;
import com.enigma.wmbapi.services.MTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MTableServiceImpl implements MTableService {
    private final MTableRepository tableRepository;
    @Override
    public MTable getById(String id) {
        return tableRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found"));
    }
}
