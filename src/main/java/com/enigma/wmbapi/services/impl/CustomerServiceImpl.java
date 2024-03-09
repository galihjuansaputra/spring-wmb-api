package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchCustomerRequest;
import com.enigma.wmbapi.dto.request.UpdateCustomerRequest;
import com.enigma.wmbapi.dto.response.CustomerResponse;
import com.enigma.wmbapi.entity.Customer;
import com.enigma.wmbapi.repository.CustomerRepository;
import com.enigma.wmbapi.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(NewCustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Override
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        if (request.getName() != null || request.getPhoneNumber() != null) {
            Page<CustomerResponse> result = customerRepository.findAllByNameContainingIgnoreCaseOrPhoneNumberContaining(request.getName(), request.getPhoneNumber(), pageable);

            if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

            return result;
        }

        Page<Customer> customers = customerRepository.findAll(pageable);

        List<CustomerResponse> customerResponses = customers.getContent()
                .stream()
                .map(this::convertCustomerToCustomerResponse)
                .toList();

        Page<CustomerResponse> result = new PageImpl<>(customerResponses, pageable, customers.getTotalElements());

        if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

        return result;
    }

    @Override
    public CustomerResponse update(UpdateCustomerRequest customer) {

        Customer currentCustomer = getById(customer.getId());
        currentCustomer.setName(customer.getName());
        currentCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.saveAndFlush(currentCustomer);
        return convertCustomerToCustomerResponse(currentCustomer);
    }

    @Override
    public void delete(String id) {
        Customer currentCustomer = getById(id);
        customerRepository.delete(currentCustomer);
    }

    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }


}
