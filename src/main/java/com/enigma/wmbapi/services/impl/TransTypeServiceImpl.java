package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.entity.TransType;
import com.enigma.wmbapi.repository.TransTypeRepository;
import com.enigma.wmbapi.services.TransTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransTypeServiceImpl implements TransTypeService {
    private final TransTypeRepository transTypeRepository;
    @Override
    public TransType getById(String id) {
        return transTypeRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found"));
    }
}
