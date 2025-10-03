package com.community.marketplace.controller;

import com.community.marketplace.dto.ReviewCreateDto;
import com.community.marketplace.dto.ReviewResponseDto;
import com.community.marketplace.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @RequestBody ReviewCreateDto dto,
            Authentication authentication
    ) {
        String email = authentication.getName(); 
        return ResponseEntity.ok(reviewService.createReview(dto, email));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<ReviewResponseDto>> getByProfessional(
            @PathVariable Long professionalId
    ) {
        return ResponseEntity.ok(reviewService.getByProfessional(professionalId));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<ReviewResponseDto>> getMyReviews(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reviewService.getMyReviews(email));
    }
}
