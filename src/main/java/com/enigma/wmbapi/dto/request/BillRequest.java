package com.enigma.wmbapi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BillRequest {
    private String customerId;
    private String tableId;
    private String transType;
    private List<BillDetailRequest> billDetails;
}
