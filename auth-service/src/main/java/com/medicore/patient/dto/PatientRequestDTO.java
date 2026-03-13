package com.medicore.patient.dto;

import java.time.LocalDate;

import com.medicore.patient.entity.BloodGroup;
import com.medicore.patient.entity.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
	
	 @NotBlank(message = "First name is required")
	    @Size(max = 50)
	    private String firstName;

	    @NotBlank(message = "Last name is required")
	    @Size(max = 50)
	    private String lastName;

	    @NotNull(message = "Date of birth is required")
	    private LocalDate dateOfBirth;

	    @NotNull(message = "Gender is required")
	    private Gender gender;

	    @NotBlank(message = "Phone number is required")
	    @Size(max = 15)
	    private String phoneNumber;

	    @Email(message = "Invalid email format")
	    private String email;

	    private String address;

	    private BloodGroup bloodGroup;

	    private String emergencyContactName;

	    private String emergencyContactPhone;

}
