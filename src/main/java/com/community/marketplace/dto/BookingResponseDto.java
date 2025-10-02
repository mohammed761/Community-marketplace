package com.community.marketplace.dto;

import com.community.marketplace.model.Booking;
import com.community.marketplace.model.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDto {
    private Long id;
    private String customerName;
    private String professionalName;
    private LocalDateTime date;
    private BookingStatus status;

    public BookingResponseDto(Booking booking) {
        this.id = booking.getId();
        this.customerName = booking.getCustomer().getUsername();
        this.professionalName = booking.getProfessional().getFullName();
        this.date = booking.getDate();
        this.status = booking.getStatus();
    }
}
