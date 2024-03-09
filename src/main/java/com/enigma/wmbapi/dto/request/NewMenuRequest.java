package com.enigma.wmbapi.dto.request;

import com.enigma.wmbapi.entity.Menu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link Menu}
 */
@Data
@Builder
public class NewMenuRequest {
    @NotNull
    String name;
    @Min(value = 0)
    Long price;
}