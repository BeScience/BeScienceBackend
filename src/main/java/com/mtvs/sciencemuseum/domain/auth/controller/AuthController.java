package com.mtvs.sciencemuseum.domain.auth.controller;

import com.mtvs.sciencemuseum.domain.auth.dto.JoinRequestDTO;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginRequestDTO;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginResponseDTO;
import com.mtvs.sciencemuseum.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequestDTO joinRequestDTO) {
        authService.join(joinRequestDTO);
        return ResponseEntity.ok().build();
    }
}
