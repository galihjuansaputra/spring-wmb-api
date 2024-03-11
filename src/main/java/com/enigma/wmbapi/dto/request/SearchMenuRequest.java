package com.enigma.wmbapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String name;
}
