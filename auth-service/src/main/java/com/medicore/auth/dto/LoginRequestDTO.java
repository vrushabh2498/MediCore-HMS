package com.medicore.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
	@NotBlank(message = "EMAIL SHOULD NOT BLANK")
	@Email(message = "CHECK FORMET OF EMAIL")
	private String email;
	
	@NotBlank(message ="ENTER THE PASSWORD")
	private String password;

}
