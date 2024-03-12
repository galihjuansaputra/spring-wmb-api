package com.enigma.wmbapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBillStatusRequest {
    private String orderId;
    private String billStatus;
}
