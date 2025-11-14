package com.challenge.wazejob.entities;

import com.challenge.wazejob.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "User's full (or not) name", example = "Pedro Guidotte")
    private String name;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    @Schema(description = "User's email address", example = "peguidotte@gmail.com", format = "email")
    private String email;

    @NotBlank
    @Size(min = 7)
    @Schema(description = "A strong password please", example = "strongPassword123")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Schema(description = "Your GitHub profile URL", example = "https://github.com/peguidotte")
    private String github;
}
