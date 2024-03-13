package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.BillDetailRequest;
import com.enigma.wmbapi.dto.request.BillRequest;
import com.enigma.wmbapi.dto.request.SearchBillRequest;
import com.enigma.wmbapi.dto.response.BillResponse;
import com.enigma.wmbapi.entity.*;
import com.enigma.wmbapi.repository.BillRepository;
import com.enigma.wmbapi.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @MockBean
    private BillService billService;
    @Mock
    private BillRepository billRepository;
    @Mock
    private BillDetailService billDetailService;
    @Mock
    private CustomerService customerService;
    @Mock
    private MenuService menuService;
    @Mock
    private MTableService tableService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private TransTypeService transTypeService;

    @BeforeEach
    void setUp(){
        billService = new BillServiceImpl(
                billRepository,
                billDetailService,
                customerService,
                tableService,
                menuService,
                transTypeService,
                paymentService

        );
    }

    // Test if the create method successfully saving data
    @Test
    void testCreateBillWithValidDetails() {
        // Given
        BillRequest request = BillRequest.builder()
                .customerId("customer-1")
                .tableId("table-1")
                .transTypeId("EI")
                .billDetails(List.of(
                        BillDetailRequest.builder()
                                .menuId("menu-1")
                                .quantity(1)
                                .build(),
                        BillDetailRequest.builder()
                                .menuId("menu-2")
                                .quantity(2)
                                .build()
                ))
                .build();

        Customer mockCustomer = Customer.builder()
                .id(request.getCustomerId())
                .build();

        MTable mockTable = MTable.builder()
                .id(request.getTableId())
                .name("1-table")
                .build();

        TransType mockTransType = TransType.builder()
                .id(request.getTransTypeId())
                .description("Eat In")
                .build();

        List<BillDetail> mockBillDetail = new ArrayList<>();

        Payment payment = Payment.builder()
                .id("pay-1")
                .redirectUrl("url")
                .token("token")
                .billStatus("status")
                .build();

        Bill trx = Bill.builder()
                .id("trx-id")
                .transDate(new Date())
                .customer(mockCustomer)
                .table(mockTable)
                .transType(mockTransType)
                .billDetails(mockBillDetail)
                .payment(payment)
                .build();

        billRepository.saveAndFlush(trx);
    }

    // Test if the getAll method returns a page of bill responses
    @Test
    void shouldReturnPageBillResponseWhenGetAll() {
        // Given
        SearchBillRequest request = SearchBillRequest.builder()
                .page(10)
                .size(5)
                .build();
        Pageable pageable = PageRequest.of(request.getPage(),request.getSize());
        List<BillDetail> trxDetail = new ArrayList<>();
        Bill trx1 = Bill.builder()
                .id("1")
                .customer(Customer.builder().build())
                .billDetails(trxDetail)
                .transDate(new Date())
                .transType(TransType.builder().build())
                .table(MTable.builder().build())
                .build();
        Bill trx2 = Bill.builder()
                .id("2")
                .customer(Customer.builder().build())
                .billDetails(trxDetail)
                .transDate(new Date())
                .transType(TransType.builder().build())
                .table(MTable.builder().build())
                .build();

        List<Bill> trx = List.of(trx1,trx2);

        Page<Bill> responses = new PageImpl<>(trx);

        // Stubbing
        Mockito.when(billRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(responses);

        // When
        Page<BillResponse> all = billService.getAll(request);

        // Then
        assertNotNull(all);
        assertEquals(2, all.stream().count());
    }

}