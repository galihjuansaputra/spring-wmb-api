package com.enigma.wmbapi.dto.request;

import com.enigma.wmbapi.entity.Menu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for {@link Menu}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMenuRequest {
    @NotNull
    String name;
    @Min(value = 0)
    Long price;

    private MultipartFile image;
}