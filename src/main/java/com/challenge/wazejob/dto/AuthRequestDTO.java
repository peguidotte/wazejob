package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Credentials used to request a JWT token")
public class AuthRequestDTO {

    @NotBlank
    @Email
    @Schema(description = "Account email", example = "peguidotte@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Account password", example = "strongPassword123")
    private String password;
}

