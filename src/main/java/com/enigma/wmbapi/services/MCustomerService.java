package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import org.springframework.data.domain.Page;

public interface MCustomerService {

    MCustomer create(NewMCustomerRequest request);
    MCustomer getById(String id);


    Page<MCustomer> getAll(SearchMCustomerRequest request);

    MCustomer update(MCustomer customer);
    void delete(String id);

}
