package com.enigma.wmbapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditMCustomerRequest {
    @NotNull
    String name;
    @NotNull
    String phoneNumber;
}
