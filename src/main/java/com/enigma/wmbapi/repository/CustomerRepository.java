package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.dto.response.CustomerResponse;
import com.enigma.wmbapi.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Page<CustomerResponse> findAllByNameContainingIgnoreCaseOrPhoneNumberContaining(String name, String phoneNumber, Pageable pageable);


}
