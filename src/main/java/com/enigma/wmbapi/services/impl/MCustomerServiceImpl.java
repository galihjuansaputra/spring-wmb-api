package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.EditMCustomerRequest;
import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.repository.MCustomerRepository;
import com.enigma.wmbapi.services.MCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Page<MCustomer> getAll(SearchMCustomerRequest request) {
        if(request.getPage() == 0){
            request.setPage(1);
        }
        return null;
    }

    @Override
    public MCustomer update(EditMCustomerRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
