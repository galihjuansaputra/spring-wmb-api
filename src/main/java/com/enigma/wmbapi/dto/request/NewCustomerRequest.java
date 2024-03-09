package com.enigma.wmbapi.dto.request;

import com.enigma.wmbapi.entity.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link Customer}
 */
@Data
@Builder
public class NewCustomerRequest {
    @NotNull
    String name;
    @NotNull
    String phoneNumber;
}