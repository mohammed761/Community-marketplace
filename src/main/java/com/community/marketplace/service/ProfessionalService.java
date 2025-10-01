package com.community.marketplace.service;

import com.community.marketplace.dto.ProfessionalCreateDto;
import com.community.marketplace.dto.ProfessionalResponseDto;
import com.community.marketplace.model.Professional;
import com.community.marketplace.model.ProfessionalType;
import com.community.marketplace.model.User;
import com.community.marketplace.repository.ProfessionalRepository;
import com.community.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final UserRepository userRepository;

    public ProfessionalResponseDto createProfile(ProfessionalCreateDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (professionalRepository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Profile already exists for this user");
        }

        Professional professional = Professional.builder()
                .fullName(dto.getFullName())
                .description(dto.getDescription())
                .type(dto.getType())
                .available(true)
                .user(user)
                .build();

        professionalRepository.save(professional);
        return mapToDto(professional);
    }

    public List<ProfessionalResponseDto> getAllProfessionals(ProfessionalType type) {
        List<Professional> professionals;
        if (type != null) {
            professionals = professionalRepository.findByType(type);
        } else {
            professionals = professionalRepository.findAll();
        }
        return professionals.stream().map(this::mapToDto).toList();
    }

    public ProfessionalResponseDto getProfessionalById(Long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));
        return mapToDto(professional);
    }

    private ProfessionalResponseDto mapToDto(Professional professional) {
        return ProfessionalResponseDto.builder()
                .id(professional.getId())
                .fullName(professional.getFullName())
                .description(professional.getDescription())
                .type(professional.getType())
                .available(professional.isAvailable())
                .userId(professional.getUser().getId())
                .email(professional.getUser().getEmail())
                .build();
    }
}
