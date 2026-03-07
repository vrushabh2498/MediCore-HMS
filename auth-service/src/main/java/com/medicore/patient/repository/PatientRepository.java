package com.medicore.patient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	List<Patient> findByActiveTrue();

    Optional<Patient> findByIdAndActiveTrue(Long id);

    boolean existsByEmail(String email);
}
