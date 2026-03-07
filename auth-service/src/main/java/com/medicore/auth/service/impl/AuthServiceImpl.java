package com.medicore.auth.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.auth.dto.ApiResponse;
import com.medicore.auth.dto.LoginRequestDTO;
import com.medicore.auth.dto.LoginResponseDTO;
import com.medicore.auth.dto.RegisterRequestDTO;
import com.medicore.auth.dto.RegisterResponseDTO;
import com.medicore.auth.entity.Role;
import com.medicore.auth.entity.User;
import com.medicore.auth.entity.UserRole;
import com.medicore.auth.exception.InvalidCredentialsException;
import com.medicore.auth.exception.UserAlreadyExistsException;
import com.medicore.auth.repository.RoleRepository;
import com.medicore.auth.repository.UserRepository;
import com.medicore.auth.repository.UserRoleRepository;
import com.medicore.auth.security.JwtUtil;
import com.medicore.auth.service.AuthService;

import jakarta.transaction.Transactional;
@Service
public class AuthServiceImpl implements AuthService{
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
  
	public AuthServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder,
			JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	@Transactional
	public ApiResponse<RegisterResponseDTO> registerUser(RegisterRequestDTO request) {
		
		logger.info("Registration request received for username: {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
        	logger.warn("Duplicate username attempted: {}", request.getUsername());
        	throw new UserAlreadyExistsException("Username already exists");
        }

       
        if (userRepository.existsByEmail(request.getEmail())) {
        	logger.warn("Duplicate Email attempted: {}", request.getEmail());
        	throw new UserAlreadyExistsException("Email already exists");
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
        
        logger.info("User registered successfully with id: {}", savedUser.getId());
	
	 return new ApiResponse<>(
             "SUCCESS",
             "User registered successfully",
             responseDTO
             
     );
}
	private static final Logger logger =
	        LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public ApiResponse<LoginResponseDTO> loginUser(LoginRequestDTO request) {
		logger.info("Login attempt for email: {}", request.getEmail());
		
		User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new InvalidCredentialsException("Invalid credentials"));
		
		if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
			logger.warn("Invalid login attempt for email: {}", request.getEmail());
			
			 throw new InvalidCredentialsException("Invalid credentials");
		}
		
		
		
		if(!user.isEnabled()
			|| !user.isAccountNonLocked()
            || !user.isAccountNonExpired()
            || !user.isCredentialsNonExpired()){
			
			logger.warn("Login blocked due to account status for email:{}",request.getEmail());	
			
			 throw new InvalidCredentialsException("Invalid credentials");
            }
		
		logger.info("User logged in successfully: {}", user.getEmail());
		String token = jwtUtil.generateToken(user.getEmail());
		LoginResponseDTO responseDTO = new LoginResponseDTO(
	            user.getId(),
	            user.getEmail(),
                token
	            
	    );
		
		return new ApiResponse<>("SUCCESS", "Login successful", responseDTO);
		
	}
}
