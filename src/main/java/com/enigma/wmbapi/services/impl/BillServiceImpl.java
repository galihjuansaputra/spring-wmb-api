package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.constant.ResponseMessage;
import com.enigma.wmbapi.dto.request.BillRequest;
import com.enigma.wmbapi.dto.request.SearchBillRequest;
import com.enigma.wmbapi.dto.request.UpdateBillStatusRequest;
import com.enigma.wmbapi.dto.response.BillDetailResponse;
import com.enigma.wmbapi.dto.response.BillResponse;
import com.enigma.wmbapi.dto.response.PaymentResponse;
import com.enigma.wmbapi.entity.*;
import com.enigma.wmbapi.repository.BillRepository;
import com.enigma.wmbapi.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailService billDetailService;
    private final CustomerService customerService;
    private final MTableService tableService;
    private final MenuService menuService;
    private final TransTypeService transTypeService;
    private final PaymentService paymentService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        MTable table = tableService.getById(request.getTableId());
        TransType transType = transTypeService.getById(request.getTransTypeId());

        Bill bill = Bill.builder()
                .transDate(new Date())
                .customer(customer)
                .table(table)
                .transType(transType)
                .build();
        billRepository.saveAndFlush(bill);

        List<BillDetail> billDetails = request.getBillDetails().stream().map(detailRequest -> {
            Menu menu = menuService.getById(detailRequest.getMenuId());
            menuService.update(menu);

            return BillDetail.builder()
                    .menu(menu)
                    .bill(bill)
                    .qty(detailRequest.getQuantity())
                    .price(menu.getPrice())
                    .build();
        }).toList();

        List<BillDetailResponse> billDetailResponses = billDetails.stream().map(detail ->
                BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuId(detail.getMenu().getId())
                        .price(detail.getPrice())
                        .quantity(detail.getQty())
                        .build()
        ).toList();

        billDetailService.createBulk(billDetails);

        bill.setBillDetails(billDetails);
        Payment payment = paymentService.createPayment(bill);
        bill.setPayment(payment);


        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getBillStatus())
                .build();

        return BillResponse.builder()
                .id(bill.getId())
                .customer(bill.getCustomer().getId())
                .tableId(bill.getTable().getId())
                .transDate(bill.getTransDate())
                .transType(bill.getTransType().getId())
                .billDetails(billDetailResponses)
                .paymentResponse(paymentResponse)
                .build();
    }

    @Override
    public Page<BillResponse> getAll(SearchBillRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize());
        Page<Bill> transactions = billRepository.findAll(pageable);

        return transactions.map(bill -> {
            List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(detail -> BillDetailResponse.builder()
                    .id(detail.getId())
                    .menuId(detail.getMenu().getId())
                    .price(detail.getPrice())
                    .quantity(detail.getQty())
                    .build()).toList();

            return BillResponse.builder()
                    .id(bill.getId())
                    .customer(bill.getCustomer().getId())
                    .transDate(bill.getTransDate())
                    .transType(bill.getTransType().getId())
                    .tableId(bill.getTable().getId())
                    .billDetails(billDetailResponses)
                    .build();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(UpdateBillStatusRequest request){
        Bill bill = billRepository.findById(request.getOrderId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND)
        );
        Payment payment = bill.getPayment();
        payment.setBillStatus(request.getBillStatus());
    }
}
