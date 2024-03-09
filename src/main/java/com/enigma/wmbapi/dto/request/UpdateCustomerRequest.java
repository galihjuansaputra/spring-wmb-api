package com.enigma.wmbapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCustomerRequest {
    private String id;
    private String name;
    private String phoneNumber;
}
