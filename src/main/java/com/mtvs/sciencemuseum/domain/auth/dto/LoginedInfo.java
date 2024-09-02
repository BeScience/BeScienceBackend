package com.mtvs.sciencemuseum.domain.auth.dto;

import com.mtvs.sciencemuseum.domain.user.entity.Role;
import lombok.Data;

@Data
public class LoginedInfo {
    private String username;
    private Role role;
}
