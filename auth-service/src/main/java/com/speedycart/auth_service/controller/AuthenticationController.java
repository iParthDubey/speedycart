package com.speedycart.auth_service.controller;

import com.speedycart.auth_service.dto.AuthResponse;
import com.speedycart.auth_service.dto.RegisterRequest;
import com.speedycart.auth_service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        try {
            AuthResponse authResponse = authenticationService.register(request);
            return  ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        }catch (RuntimeException e){
            log.error("Registration Failed L {}", e.getMessage());
            throw e;
        }
    }
}
