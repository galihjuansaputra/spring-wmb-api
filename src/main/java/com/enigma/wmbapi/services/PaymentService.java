package com.enigma.wmbapi.services;

import com.enigma.wmbapi.entity.Bill;
import com.enigma.wmbapi.entity.Payment;

public interface PaymentService {
    Payment createPayment(Bill bill);

}
