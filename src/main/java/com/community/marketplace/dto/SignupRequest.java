package com.community.marketplace.dto;

import com.community.marketplace.model.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
