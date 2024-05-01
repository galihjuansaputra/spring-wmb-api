package com.enigma.wmbapi.dto.request;

import com.enigma.wmbapi.entity.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for {@link Customer}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCustomerRequest {
    @NotNull
    String name;
    @NotNull
    String phoneNumber;
}