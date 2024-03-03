package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.entity.MCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCustomerRepository extends JpaRepository<MCustomer, String> {

    Page<MCustomer> findAllByNameContainingIgnoreCaseOrPhoneNumberContaining(String name,String phoneNumber, Pageable pageable);

}
