package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.PaymentDetailRequest;
import com.enigma.wmbapi.dto.request.PaymentItemDetailRequest;
import com.enigma.wmbapi.dto.request.PaymentRequest;
import com.enigma.wmbapi.entity.Payment;
import com.enigma.wmbapi.entity.Menu;
import com.enigma.wmbapi.entity.Bill;
import com.enigma.wmbapi.entity.BillDetail;
import com.enigma.wmbapi.repository.PaymentRepository;
import com.enigma.wmbapi.services.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final String SECRET_KEY;
    private final String BASE_URL_SNAP;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository, RestClient restClient,
            @Value("${midtrans.api.key}") String secretKey,
            @Value("${midtrans.api.snap-url}") String baseUrlSnap
    ) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        SECRET_KEY = secretKey;
        BASE_URL_SNAP = baseUrlSnap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(Bill bill) {
        long amount = bill.getBillDetails()
                .stream().mapToLong(value -> (value.getQty() * value.getPrice()))
                .reduce(0, Long::sum);

        List<PaymentItemDetailRequest> itemDetailRequestsList = bill.getBillDetails().stream()
                .map(transactionDetail -> PaymentItemDetailRequest.builder()
                        .name(transactionDetail.getMenu().getName())
                        .price(transactionDetail.getPrice())
                        .quantity(transactionDetail.getQty())
                        .build()).toList();
        PaymentRequest request = PaymentRequest.builder()
                .paymentDetail(PaymentDetailRequest.builder()
                        .orderId(bill.getId())
                        .amount(amount)
                        .build())
                .paymentItemDetails(itemDetailRequestsList)
                .paymentMethod(List.of("shopeepay","gopay"))
                .build();

        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(BASE_URL_SNAP)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + SECRET_KEY)
                .retrieve().toEntity(new ParameterizedTypeReference<>() {});

        Map<String, String> body = response.getBody();

        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .billStatus("ordered")
                .build();
        paymentRepository.saveAndFlush(payment);

        return payment;
    }


}
