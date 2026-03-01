package com.medicore.auth.service;

import com.medicore.auth.dto.ApiResponse;
import com.medicore.auth.dto.LoginRequestDTO;
import com.medicore.auth.dto.LoginResponseDTO;
import com.medicore.auth.dto.RegisterRequestDTO;
import com.medicore.auth.dto.RegisterResponseDTO;

public interface AuthService {
	ApiResponse<RegisterResponseDTO> registerUser(RegisterRequestDTO request);
	ApiResponse<LoginResponseDTO> loginUser(LoginRequestDTO request);
}
