package com.medicore.patient.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.medicore.auth.security.JwtUtil;
import com.medicore.patient.dto.ApiResponse;
import com.medicore.patient.dto.PatientRequestDTO;
import com.medicore.patient.dto.PatientResponseDTO;
import com.medicore.patient.service.PatientService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@Component
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final JwtUtil jwtUtil;
    private final PatientService patientService;

    public PatientController(PatientService patientService, JwtUtil jwtUtil) {
        this.patientService = patientService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(
            @Valid @RequestBody PatientRequestDTO request,
            HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        String token = authHeader.substring(7);

        Claims claims = jwtUtil.extractAllClaims(token);
        Long createdByUserId = claims.get("userId", Long.class);

        PatientResponseDTO patient = patientService.createPatient(request, createdByUserId);

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS",
                        "Patient created successfully",
                        patient)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getAllPatients() {

        List<PatientResponseDTO> patients = patientService.getAllPatients();

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS",
                        "Patients fetched successfully",
                        patients)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable Long id) {

        ApiResponse<PatientResponseDTO> patient = patientService.getPatientById(id);

        return ResponseEntity.ok(patient);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO request) {

        PatientResponseDTO response = patientService.updatePatient(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Patient updated successfully", response)
        );
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Patient deleted successfully", null)
        );
    }
    
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<PatientResponseDTO>>> getPatientsWithPagination(
            @RequestParam int page,
            @RequestParam int size) {

        Page<PatientResponseDTO> patients =
                patientService.getPatientsWithPagination(page, size);

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Patients fetched successfully", patients)
        );
    }
}