package com.medicore.patient.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.medicore.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	List<Patient> findByActiveTrue();

    Optional<Patient> findByIdAndActiveTrue(Long id);

    boolean existsByEmail(String email);
    
    Page<Patient> findByActiveTrue(Pageable pageable);
}
