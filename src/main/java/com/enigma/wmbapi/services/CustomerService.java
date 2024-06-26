package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.request.NewCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchCustomerRequest;
import com.enigma.wmbapi.dto.request.UpdateCustomerRequest;
import com.enigma.wmbapi.dto.response.CustomerResponse;
import com.enigma.wmbapi.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {

    Customer create(NewCustomerRequest request);
    Customer create(Customer requesst);
    Customer getById(String id);

    Page<CustomerResponse> getAll(SearchCustomerRequest request);

    CustomerResponse update(UpdateCustomerRequest customer);
    void delete(String id);

}
