package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findAllByBillStatusIn(List<String> billStatus);
}
