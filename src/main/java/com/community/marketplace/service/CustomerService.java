package com.community.marketplace.service;

import com.community.marketplace.dto.CustomerResponseDto;
import com.community.marketplace.model.Customer;
import com.community.marketplace.model.User;
import com.community.marketplace.repository.CustomerRepository;
import com.community.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerResponseDto getMyProfile(String email) {
        Customer customer = customerRepository.findByUserEmail(email)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    
                    Customer newCustomer = Customer.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .user(user)
                            .build();

                    return customerRepository.save(newCustomer);
                });

        return CustomerResponseDto.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .build();
    }
}
