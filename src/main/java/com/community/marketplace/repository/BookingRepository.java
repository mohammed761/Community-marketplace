package com.community.marketplace.repository;

import com.community.marketplace.model.Booking;
import com.community.marketplace.model.Customer;
import com.community.marketplace.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByProfessional(Professional professional);
}
