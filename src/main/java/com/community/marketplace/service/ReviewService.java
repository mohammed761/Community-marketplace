package com.community.marketplace.service;

import com.community.marketplace.dto.ReviewCreateDto;
import com.community.marketplace.dto.ReviewResponseDto;
import com.community.marketplace.model.*;
import com.community.marketplace.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewCreateDto dto, String customerEmail) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Customer bookingCustomer = booking.getCustomer();
        if (bookingCustomer == null || !customerEmail.equalsIgnoreCase(bookingCustomer.getEmail())) {
            throw new RuntimeException("You are not the owner of this booking");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new RuntimeException("You can only review a COMPLETED booking");
        }

        if (reviewRepository.existsByBooking_Id(booking.getId())) {
            throw new RuntimeException("Review already exists for this booking");
        }

        Customer customer = customerRepository.findByUserEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Professional professional = booking.getProfessional();
        if (professional == null) {
            throw new RuntimeException("Professional not found for this booking");
        }

        Review review = Review.builder()
                .booking(booking)
                .customer(customer)
                .professional(professional)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getByProfessional(Long professionalId) {
        professionalRepository.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        return reviewRepository.findAllByProfessional_IdOrderByCreatedAtDesc(professionalId)
                .stream().map(this::toDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getMyReviews(String customerEmail) {
        Customer me = customerRepository.findByUserEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return reviewRepository.findAllByCustomer_IdOrderByCreatedAtDesc(me.getId())
                .stream().map(this::toDto).collect(toList());
    }

    private ReviewResponseDto toDto(Review r) {
        return ReviewResponseDto.builder()
                .id(r.getId())
                .rating(r.getRating())
                .comment(r.getComment())
                .createdAt(r.getCreatedAt())
                .bookingId(r.getBooking().getId())
                .professionalId(r.getProfessional().getId())
                .professionalName(safeProName(r.getProfessional()))
                .customerId(r.getCustomer().getId())
                .customerName(safeCustName(r.getCustomer()))
                .build();
    }

    private String safeProName(Professional p) {
        if (p == null) return null;

        if (p.getFullName() != null && !p.getFullName().trim().isEmpty()) {
            return p.getFullName();
        }

        User u = p.getUser();
        if (u != null) {
            if (u.getUsername() != null && !u.getUsername().trim().isEmpty()) {
                return u.getUsername();
            }
            return u.getEmail();
        }

        return null;
    }

    private String safeCustName(Customer c) {
        if (c == null) return null;

        if (c.getUsername() != null && !c.getUsername().trim().isEmpty()) {
            return c.getUsername();
        }

        if (c.getEmail() != null && !c.getEmail().trim().isEmpty()) {
            return c.getEmail();
        }

        User u = c.getUser();
        if (u != null) {
            if (u.getUsername() != null && !u.getUsername().trim().isEmpty()) {
                return u.getUsername();
            }
            return u.getEmail();
        }

        return null;
    }
}
