package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for updating an existing user")
public class UserUpdateDTO {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Schema(description = "User's full name", example = "Pedro Guidotte")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters and must be valid")
    @Schema(description = "User's email address", example = "peguidotte@gmail.com", format = "email")
    private String email;

    @Size(min = 7, max = 100, message = "Password must be at between 7 and 100 characters")
    @Schema(description = "User password (minimum 7 characters)", example = "newStrongPassword123")
    private String password;
}
