package com.community.marketplace.controller;

import com.community.marketplace.dto.ProfessionalCreateDto;
import com.community.marketplace.dto.ProfessionalResponseDto;
import com.community.marketplace.dto.AvailabilityUpdateDto;
import com.community.marketplace.model.ProfessionalType;
import com.community.marketplace.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping("/profile")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalResponseDto> createProfile(
            @RequestBody ProfessionalCreateDto dto,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(professionalService.createProfile(dto, email));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponseDto>> getAll(
            @RequestParam(required = false) ProfessionalType type) {
        return ResponseEntity.ok(professionalService.getAllProfessionals(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(professionalService.getProfessionalById(id));
    }
    @PutMapping("/availability")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalResponseDto> updateAvailability(
            @RequestBody AvailabilityUpdateDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(professionalService.updateAvailability(dto.isAvailable(), email));
    }

}
