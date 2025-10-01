package com.community.marketplace.dto;

import com.community.marketplace.model.ProfessionalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalCreateDto {

    @NotBlank
    private String fullName;

    private String description;

    @NotNull
    private ProfessionalType type;
}
