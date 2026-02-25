package com.medicore.auth.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.medicore.auth.entity.Role;
import com.medicore.auth.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;
	
	public  DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
	
	@Override
	public void run(String... args) throws Exception {
		
		 if (!roleRepository.existsByName("ROLE_USER")) {
	            Role userRole = new Role();
	            userRole.setName("ROLE_USER");
	            userRole.setDescription("Default user role");
	            roleRepository.save(userRole);
	        }
		 
		 if (!roleRepository.existsByName("ROLE_ADMIN")) {
	            Role adminRole = new Role();
	            adminRole.setName("ROLE_ADMIN");
	            adminRole.setDescription("Administrator role");
	            roleRepository.save(adminRole);
	        }
		
	}

}
