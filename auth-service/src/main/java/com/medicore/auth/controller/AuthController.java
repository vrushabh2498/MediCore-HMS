package com.medicore.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.auth.dto.ApiResponse;
import com.medicore.auth.dto.RegisterRequestDTO;
import com.medicore.auth.dto.RegisterResponseDTO;
import com.medicore.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	 private final AuthService authService;

	    public AuthController(AuthService authService) {
	        this.authService = authService;
	    }
	    @PostMapping("/register")
	    public ResponseEntity<ApiResponse<RegisterResponseDTO>> registerUser(
	         @Valid   @RequestBody RegisterRequestDTO request) {

	        ApiResponse<RegisterResponseDTO> response =
	                authService.registerUser(request);

	        return ResponseEntity.ok(response);
	    }

}
