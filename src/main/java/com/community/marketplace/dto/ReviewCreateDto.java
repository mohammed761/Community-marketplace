package com.community.marketplace.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewCreateDto {
    @NotNull
    private Long bookingId;

    @Min(1) @Max(5)
    private int rating;

    private String comment;
}
