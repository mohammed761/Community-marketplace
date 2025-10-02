package com.community.marketplace.controller;

import com.community.marketplace.dto.BookingCreateDto;
import com.community.marketplace.dto.BookingResponseDto;
import com.community.marketplace.dto.BookingStatusUpdateDto;
import com.community.marketplace.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createBooking(@RequestBody BookingCreateDto dto, Authentication authentication) {
        try {
            String email = authentication.getName();
            return ResponseEntity.ok(bookingService.createBooking(dto, email));
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<BookingResponseDto> getCustomerBookings(Authentication authentication) {
        return bookingService.getCustomerBookings(authentication.getName());
    }

    @GetMapping("/professional")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public List<BookingResponseDto> getProfessionalBookings(Authentication authentication) {
        return bookingService.getProfessionalBookings(authentication.getName());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('CUSTOMER','PROFESSIONAL')")
    public ResponseEntity<BookingResponseDto> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody BookingStatusUpdateDto dto,
            Authentication authentication) {

        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, dto, role));
    }
}
