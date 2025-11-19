package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for creating a new user")
public class UserCreateDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Schema(description = "User's full name", example = "Pedro Guidotte")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "User's email address", example = "peguidotte@gmail.com", format = "email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 7, message = "Password must be at least 7 characters")
    @Schema(description = "User password (minimum 7 characters)", example = "strongPassword123")
    private String password;
}

