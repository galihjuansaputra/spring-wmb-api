package com.enigma.wmbapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchCustomerRequest {
    private String name;
    private String phoneNumber;
    private String sortBy;
    private Integer page;
    private Integer size;
    private String direction;



}
