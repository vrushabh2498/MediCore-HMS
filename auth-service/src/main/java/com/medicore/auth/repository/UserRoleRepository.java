package com.medicore.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicore.auth.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	

}
