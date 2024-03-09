package com.enigma.wmbapi.dto.response;

import com.enigma.wmbapi.entity.BillDetail;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BillResponse {
    private String id;
    private String customer;
    private Date transDate;
    private String tableId;
    private String transType;
    private List<BillDetailResponse> billDetails;

}
