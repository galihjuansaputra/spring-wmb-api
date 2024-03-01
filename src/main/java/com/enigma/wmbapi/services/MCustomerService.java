package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.request.EditMCustomerRequest;
import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.entity.MCustomer;
import org.springframework.data.domain.Page;

public interface MCustomerService {

    MCustomer create(NewMCustomerRequest request);
    MCustomer getById(String id);
    Page<MCustomer> getAll(NewMCustomerRequest request);
    MCustomer update(EditMCustomerRequest request);
    void delete(String id);

}
