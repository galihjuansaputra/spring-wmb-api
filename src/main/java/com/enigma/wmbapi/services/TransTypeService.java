package com.enigma.wmbapi.services;

import com.enigma.wmbapi.entity.TransType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransTypeService{
    TransType getById(String id);
}
