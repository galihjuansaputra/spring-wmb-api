package com.enigma.wmbapi.services;

import com.enigma.wmbapi.entity.BillDetail;

import java.util.List;

public interface BillDetailService {
    List<BillDetail> createBulk(List<BillDetail> billDetails);
}
