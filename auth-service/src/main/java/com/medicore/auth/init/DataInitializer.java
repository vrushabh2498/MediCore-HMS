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
		
		 createRoleIfNotExists("ROLE_ADMIN", "Administrator role");
		    createRoleIfNotExists("ROLE_DOCTOR", "Doctor role");
		    createRoleIfNotExists("ROLE_RECEPTIONIST", "Receptionist role");
		    createRoleIfNotExists("ROLE_USER", "Default user role");
		}

	private void createRoleIfNotExists(String name, String description) {
		if(!roleRepository.existsByName(name)) {
			Role role=new Role();
			role.setName(name);
			role.setDescription(description);
			roleRepository.save(role);
		
	}
		
	}

}
