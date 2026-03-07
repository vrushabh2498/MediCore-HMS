package com.medicore.patient.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medicore.patient.dto.ApiResponse;
import com.medicore.patient.dto.PatientRequestDTO;
import com.medicore.patient.dto.PatientResponseDTO;
import com.medicore.patient.entity.Patient;
import com.medicore.patient.exception.PatientAlreadyExitsException;
import com.medicore.patient.exception.PatientNotFound;
import com.medicore.patient.repository.PatientRepository;
import com.medicore.patient.service.PatientService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
 
	private final PatientRepository patientRepository;
	
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository=patientRepository;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
	@Override
	public PatientResponseDTO createPatient(PatientRequestDTO request, Long createdByUserId) {
		logger.info("Creating new patient with email: {}", request.getEmail());
		
		if(request.getEmail() != null && patientRepository.existsByEmail(request.getEmail())) {
			logger.warn("Duplicate patient email attempted: {}", request.getEmail());
			throw new PatientAlreadyExitsException("Patient with this email already exists");
		}
		Patient patient = new Patient();
		patient.setFirstName(request.getFirstName());
		patient.setLastName(request.getLastName());
		patient.setDateOfBirth(request.getDateOfBirth());
		patient.setGender(request.getGender());
		patient.setPhoneNumber(request.getPhoneNumber());
		patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setCreatedByUserId(createdByUserId);
        
        Patient savedPatient=patientRepository.save(patient);
        logger.info("Patient created successfully with ID: {}", savedPatient.getId());
        return mapToResponseDTO(savedPatient);
		
		
		
	}

	private PatientResponseDTO mapToResponseDTO(Patient patient) {
		
				 return new PatientResponseDTO(
				            patient.getId(),
				            patient.getFirstName(),
				            patient.getLastName(),
				            patient.getEmail(),
				            patient.getPhoneNumber(),
				            patient.getGender(),
				            patient.getBloodGroup(),
				            patient.getDateOfBirth(),
				            patient.getAddress()
				    
		   ) ;
	}

	@Override
	public List<PatientResponseDTO> getAllPatients() {
		logger.info("Fetching all active patients");
		return patientRepository.findByActiveTrue()
				.stream()
				.map(this::mapToResponseDTO)
				.collect(Collectors.toList()
						);
		
		
	}

	@Override
	public ApiResponse<PatientResponseDTO> getPatientById(Long id) {
		logger.info("Fetching patient with id ");
		Patient patient=patientRepository.findByIdAndActiveTrue(id).orElseThrow(() -> {
            logger.warn("Patient not found with id: {}", id);
            return new PatientNotFound("Patient not found with id: " + id);
        });
		PatientResponseDTO responseDTO = mapToResponseDTO(patient);

	    return new ApiResponse<>(
	            "SUCCESS",
	            "Patient fetched successfully",
	            responseDTO
	    );
	}
	
	
	

}
