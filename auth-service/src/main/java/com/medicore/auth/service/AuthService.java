package com.medicore.auth.service;

import com.medicore.auth.dto.ApiResponse;
import com.medicore.auth.dto.RegisterRequestDTO;
import com.medicore.auth.dto.RegisterResponseDTO;

public interface AuthService {
	ApiResponse<RegisterResponseDTO> registerUser(RegisterRequestDTO request);

}
