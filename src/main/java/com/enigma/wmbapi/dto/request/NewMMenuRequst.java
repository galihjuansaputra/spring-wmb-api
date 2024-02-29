package com.enigma.wmbapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.enigma.wmbapi.entity.MMenu}
 */
@Data
@Builder
public class NewMMenuRequst {
    @NotNull
    String name;
    @Min(value = 0)
    Float price;
}