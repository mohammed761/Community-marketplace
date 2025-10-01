package com.community.marketplace.dto;

import com.community.marketplace.model.ProfessionalType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalResponseDto {
    private Long id;
    private String fullName;
    private String description;
    private ProfessionalType type;
    private boolean available;
    private Long userId;
    private String email;
}
