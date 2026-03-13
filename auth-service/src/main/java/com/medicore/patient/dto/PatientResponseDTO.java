package com.medicore.patient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medicore.patient.entity.BloodGroup;
import com.medicore.patient.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
	
	public PatientResponseDTO(
	        Long id,
	        String firstName,
	        String lastName,
	        String email,
	        String phoneNumber,
	        Gender gender,
	        BloodGroup bloodGroup,
	        LocalDate dateOfBirth,
	        String address
	) {
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	    this.phoneNumber = phoneNumber;
	    this.gender = gender;
	    this.bloodGroup = bloodGroup;
	    this.dateOfBirth = dateOfBirth;
	    this.address = address;
	}
	
	private Long id;
	    private String firstName;
	    private String lastName;
	    private LocalDate dateOfBirth;
	    private Gender gender;
	    private String phoneNumber;
	    private String email;
	    private String address;
	    private BloodGroup bloodGroup;
	    private String emergencyContactName;
	    private String emergencyContactPhone;
	    private boolean active;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;

}
