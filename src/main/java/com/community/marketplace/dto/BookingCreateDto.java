package com.community.marketplace.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingCreateDto {
    private Long professionalId;
    private LocalDateTime date;
}
