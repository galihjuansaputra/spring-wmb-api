package com.enigma.wmbapi.controller;

import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.services.MCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class MCustomerController {

    private final MCustomerService mCustomerService;

    @PostMapping
    public ResponseEntity<MCustomer> createNewCustomer(@RequestBody NewMCustomerRequest request) {
        MCustomer customer = mCustomerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }



}
