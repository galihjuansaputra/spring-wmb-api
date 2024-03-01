package com.enigma.wmbapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link com.enigma.wmbapi.entity.MCustomer}
 */
@Data
@Builder
public class NewMCustomerRequest {
    @NotNull
    String name;
    @NotNull
    String phoneNumber;
}