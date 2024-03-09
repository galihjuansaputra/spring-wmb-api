package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.entity.BillDetail;
import com.enigma.wmbapi.repository.BillDetailRepository;
import com.enigma.wmbapi.services.BillDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<BillDetail> createBulk(List<BillDetail> billDetails) {
        return billDetailRepository.saveAllAndFlush(billDetails);
    }
}
