package com.enigma.wmbapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDetailRequest {
    private String menuId;
    private Integer quantity;

}
