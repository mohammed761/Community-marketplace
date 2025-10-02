package com.community.marketplace.dto;

import com.community.marketplace.model.BookingStatus;
import lombok.Data;

@Data
public class BookingStatusUpdateDto {
    private BookingStatus status;
}
