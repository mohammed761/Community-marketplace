package com.community.marketplace.controller;

import com.community.marketplace.dto.CustomerResponseDto;
import com.community.marketplace.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(customerService.getMyProfile(email));
    }
}
