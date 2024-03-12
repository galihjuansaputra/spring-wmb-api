package com.enigma.wmbapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ReportResponse {
    private String id;
    private String customer;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transDate;
    private String tableId;
    private String transType;
    private List<BillDetailResponse> billDetails;
    private String paymentId;
    private String paymentStatus;
}
