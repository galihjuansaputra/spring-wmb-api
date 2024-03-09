package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.BillRequest;
import com.enigma.wmbapi.dto.request.SearchBillRequest;
import com.enigma.wmbapi.dto.response.BillResponse;
import com.enigma.wmbapi.entity.MTable;
import com.enigma.wmbapi.repository.BillDetailRepository;
import com.enigma.wmbapi.repository.BillRepository;
import com.enigma.wmbapi.services.BillService;
import com.enigma.wmbapi.services.CustomerService;
import com.enigma.wmbapi.services.MTableService;
import com.enigma.wmbapi.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final CustomerService customerService;
    private final MTableService tableService;
    private final MenuService menuService;



    @Override
    public BillResponse create(BillRequest request) {
        return null;
    }

    @Override
    public Page<BillResponse> getAll(SearchBillRequest request) {
        return null;
    }
}
