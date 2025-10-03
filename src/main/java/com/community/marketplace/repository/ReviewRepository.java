package com.community.marketplace.repository;

import com.community.marketplace.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByBooking_Id(Long bookingId);

    List<Review> findAllByProfessional_IdOrderByCreatedAtDesc(Long professionalId);

    List<Review> findAllByCustomer_IdOrderByCreatedAtDesc(Long customerId);
}
