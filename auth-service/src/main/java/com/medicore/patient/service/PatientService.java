package com.medicore.patient.service;

import java.util.List;

import com.medicore.patient.dto.ApiResponse;
import com.medicore.patient.dto.PatientRequestDTO;
import com.medicore.patient.dto.PatientResponseDTO;

public interface PatientService {

	 PatientResponseDTO createPatient(PatientRequestDTO request, Long createdByUserId);

	    List<PatientResponseDTO> getAllPatients();

	    ApiResponse<PatientResponseDTO> getPatientById(Long id);
}
