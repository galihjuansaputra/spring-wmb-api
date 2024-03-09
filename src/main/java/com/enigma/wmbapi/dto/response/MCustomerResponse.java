package com.enigma.wmbapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MCustomerResponse {
    private String id;
    private String name;
    private String phoneNumber;
}
