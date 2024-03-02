package com.enigma.wmbapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMCustomerRequest {
    private String name;
    private String phoneNumber;
    private Integer page;
    private Integer size;
    private String direction;



}
