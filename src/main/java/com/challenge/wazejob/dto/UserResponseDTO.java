package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for user response (without password)")
public class UserResponseDTO {

    @Schema(description = "User's unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @Schema(description = "User's full name", example = "Pedro Guidotte")
    private String name;

    @Schema(description = "User's email address", example = "peguidotte@gmail.com")
    private String email;

    @Schema(description = "User's GitHub profile URL", example = "https://github.com/peguidotte")
    private String github;
}

