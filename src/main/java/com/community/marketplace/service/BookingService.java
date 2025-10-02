package com.community.marketplace.service;

import com.community.marketplace.dto.BookingCreateDto;
import com.community.marketplace.dto.BookingResponseDto;
import com.community.marketplace.dto.BookingStatusUpdateDto;
import com.community.marketplace.model.Booking;
import com.community.marketplace.model.Customer;
import com.community.marketplace.model.Professional;
import com.community.marketplace.model.BookingStatus;
import com.community.marketplace.repository.BookingRepository;
import com.community.marketplace.repository.CustomerRepository;
import com.community.marketplace.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;

    public BookingResponseDto createBooking(BookingCreateDto dto, String customerEmail) {
        Customer customer = customerRepository.findByUserEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        if (!professional.isAvailable()) {
            throw new IllegalStateException("Professional is not available");
        }

        Booking booking = Booking.builder()
                .customer(customer)
                .professional(professional)
                .date(dto.getDate())
                .status(BookingStatus.PENDING)
                .build();

        bookingRepository.save(booking);
        return new BookingResponseDto(booking);
    }

    public List<BookingResponseDto> getCustomerBookings(String customerEmail) {
        Customer customer = customerRepository.findByUserEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return bookingRepository.findByCustomer(customer)
                .stream().map(BookingResponseDto::new).toList();
    }

    public List<BookingResponseDto> getProfessionalBookings(String professionalEmail) {
        Professional professional = professionalRepository.findByUserEmail(professionalEmail)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        return bookingRepository.findByProfessional(professional)
                .stream().map(BookingResponseDto::new).toList();
    }

    public BookingResponseDto updateBookingStatus(Long bookingId, BookingStatusUpdateDto dto, String role) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (role.equals("CUSTOMER")) {
            if (booking.getStatus() == BookingStatus.PENDING && dto.getStatus() == BookingStatus.CANCELLED) {
                booking.setStatus(BookingStatus.CANCELLED);
            } else {
                throw new RuntimeException("Customer cannot change to this status");
            }
        } else if (role.equals("PROFESSIONAL")) {
            if (dto.getStatus() == BookingStatus.ACCEPTED ||
                dto.getStatus() == BookingStatus.DECLINED ||
                dto.getStatus() == BookingStatus.COMPLETED) {
                booking.setStatus(dto.getStatus());
            } else {
                throw new RuntimeException("Professional cannot set this status");
            }
        }

        bookingRepository.save(booking);
        return new BookingResponseDto(booking);
    }
}
