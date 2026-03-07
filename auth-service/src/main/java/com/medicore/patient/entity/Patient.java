package com.medicore.patient.entity;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @NotBlank(message = "First name is required")
	    @Column(nullable = false, length = 50)
	private String firstName;
	
	 @NotBlank(message = "Last name is required")
	    @Column(nullable = false, length = 50)
	private String lastName;
	
	 @NotNull(message = "Date of birth is required")
	    @Column(nullable = false)
	private LocalDate dateOfBirth;
	 
	 @NotNull(message = "Gender is required")
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	private Gender gender;
	
	 @NotBlank(message = "Phone number is required")
	    @Column(nullable = false, length = 15)
	private Long phoneNumber;
	
	 @Email(message = "Invalid email format")
	    @Column(unique = true, length = 100)
	private String email;
	
	 @Column(columnDefinition = "TEXT")
	private String address;
	
	 @Enumerated(EnumType.STRING)
	private BloodGroup bloodGroup;
	
	 @Column(length = 100)
	private String  emergencyContactName;
	
	 @Column(length = 15)
	private Long emergencyContactPhone;
	
	 @Column(nullable = false)
	private Long createdByUserId;
	
	 @Column(nullable = false)
	private boolean active;
	
	 @CreationTimestamp
	    @Column(nullable = false, updatable = false)
	private Long createdAt;
	
	 @UpdateTimestamp
	private Long updatedAt;
	

}
