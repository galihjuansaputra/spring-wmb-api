package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.entity.MCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MCustomerRepository extends JpaRepository<MCustomer, String>, JpaSpecificationExecutor<MCustomer> {

}
