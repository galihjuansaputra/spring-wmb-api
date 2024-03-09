package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchMCustomerRequest;
import com.enigma.wmbapi.dto.response.MCustomerResponse;
import com.enigma.wmbapi.dto.response.PagingResponse;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.repository.MCustomerRepository;
import com.enigma.wmbapi.services.MCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MCustomerServiceImpl implements MCustomerService {
    private final MCustomerRepository mCustomerRepository;

    @Override
    public MCustomer create(NewMCustomerRequest request) {
        MCustomer customer = MCustomer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return mCustomerRepository.saveAndFlush(customer);
    }

    @Override
    public MCustomer getById(String id) {
        return mCustomerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Override
    public Page<MCustomerResponse> getAll(SearchMCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        if (request.getName() != null || request.getPhoneNumber() != null) {
            Page<MCustomerResponse> result = mCustomerRepository.findAllByNameContainingIgnoreCaseOrPhoneNumberContaining(request.getName(), request.getPhoneNumber(), pageable);

            if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

            return result;
        }

        Page<MCustomer> customers = mCustomerRepository.findAll(pageable);

        List<MCustomerResponse> customerResponses = customers.getContent()
                .stream()
                .map(this::convertMCustomerToMCustomerResponse)
                .toList();

        Page<MCustomerResponse> result = new PageImpl<>(customerResponses, pageable, customers.getTotalElements());

        if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

        return result;
    }

    @Override
    public MCustomer update(MCustomer customer) {
        getById(customer.getId());
        return mCustomerRepository.saveAndFlush(customer);
    }

    @Override
    public void delete(String id) {
        MCustomer currentCustomer = getById(id);
        mCustomerRepository.delete(currentCustomer);
    }

    private MCustomerResponse convertMCustomerToMCustomerResponse(MCustomer customer) {
        return MCustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }


}
