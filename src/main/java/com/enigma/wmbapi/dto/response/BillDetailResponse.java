package com.enigma.wmbapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDetailResponse {
    private String id;
    private String billId;
    private String menuId;
    private Integer quantity;
    private Long price;

}
