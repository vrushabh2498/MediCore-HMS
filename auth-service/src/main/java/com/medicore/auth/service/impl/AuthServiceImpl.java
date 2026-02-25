package com.medicore.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.auth.dto.ApiResponse;
import com.medicore.auth.dto.RegisterRequestDTO;
import com.medicore.auth.dto.RegisterResponseDTO;
import com.medicore.auth.entity.Role;
import com.medicore.auth.entity.User;
import com.medicore.auth.entity.UserRole;
import com.medicore.auth.repository.RoleRepository;
import com.medicore.auth.repository.UserRepository;
import com.medicore.auth.repository.UserRoleRepository;
import com.medicore.auth.service.AuthService;

import jakarta.transaction.Transactional;
@Service
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	@Transactional
	public ApiResponse<RegisterResponseDTO> registerUser(RegisterRequestDTO request) {
		
		
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

       
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        
        User savedUser = userRepository.save(user);
        
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        
        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(role);
        
        userRoleRepository.save(userRole);
        
        RegisterResponseDTO responseDTO =
                new RegisterResponseDTO(
                        savedUser.getId(),
                        savedUser.getUsername(),
                        savedUser.getEmail()
                );
        
       
	
	 return new ApiResponse<>(
             "SUCCESS",
             "User registered successfully",
             responseDTO
     );
}
}
