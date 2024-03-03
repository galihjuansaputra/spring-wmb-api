package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.repository.MCustomerRepository;
import com.enigma.wmbapi.services.MCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MCustomerServiceImpl implements MCustomerService {
    private final MCustomerRepository mCustomerRepository;

    @Override
    public MCustomer create(NewMCustomerRequest request){
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
    public Page<MCustomer> getAll(SearchMCustomerRequest request) {
        if(request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        if (request.getName() != null) {
            return mCustomerRepository.findAllByNameContainingIgnoreCase(request.getName(), pageable);
        }
        return mCustomerRepository.findAll(pageable);
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


}
