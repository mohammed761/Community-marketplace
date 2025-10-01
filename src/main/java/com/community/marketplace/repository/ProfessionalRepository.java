package com.community.marketplace.repository;

import com.community.marketplace.model.Professional;
import com.community.marketplace.model.ProfessionalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByUserId(Long userId);
    List<Professional> findByType(ProfessionalType type);
    boolean existsByUserId(Long userId);
    Optional<Professional> findByUserEmail(String email);

}
