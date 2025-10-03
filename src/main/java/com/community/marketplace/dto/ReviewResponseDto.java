package com.community.marketplace.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewResponseDto {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    private Long bookingId;
    private Long professionalId;
    private String professionalName;

    private Long customerId;
    private String customerName;
}
