package com.enigma.wmbapi.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    private String customerId;
    private String tableId;
    private String transTypeId;
    private List<BillDetailRequest> billDetails;
}
