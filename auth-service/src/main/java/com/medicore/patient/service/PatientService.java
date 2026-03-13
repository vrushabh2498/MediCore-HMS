package com.medicore.patient.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.medicore.patient.dto.ApiResponse;
import com.medicore.patient.dto.PatientRequestDTO;
import com.medicore.patient.dto.PatientResponseDTO;

public interface PatientService {

	 PatientResponseDTO createPatient(PatientRequestDTO request, Long createdByUserId);

	    List<PatientResponseDTO> getAllPatients();

	    ApiResponse<PatientResponseDTO> getPatientById(Long id);
	    
	    PatientResponseDTO updatePatient(Long id, PatientRequestDTO request);
	    
	    void deletePatient(Long id);
	    Page<PatientResponseDTO> getPatientsWithPagination(int page, int size);
}
