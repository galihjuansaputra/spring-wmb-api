package com.enigma.wmbapi.dto.response;

import lombok.*;

@Data
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private Long price;
}
