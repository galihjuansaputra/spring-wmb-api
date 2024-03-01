package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.repository.MCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MCustomerServiceImpl {
    private final MCustomerRepository mCustomerRepository;

    public MCustomer create(NewMCustomerRequest request){

        return null;
    }
}
