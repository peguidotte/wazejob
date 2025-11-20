package com.challenge.wazejob.dto;

import com.challenge.wazejob.enums.Interest;
import com.challenge.wazejob.enums.Seniority;
import com.challenge.wazejob.enums.Skill;
import com.challenge.wazejob.validation.GithubUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "DTO for creating a profile linked to a user")
public class ProfileCreateDTO {

    @NotBlank
    @Schema(description = "User ID associated with the profile", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @GithubUrl
    @Schema(description = "User's GitHub profile URL", example = "https://github.com/peguidotte")
    private String github;

    @Size(max = 1000)
    @Schema(description = "Short biography for the user", example = "Software engineer focused on Java and AI")
    private String bio;

    @Schema(description = "Collection of technical skills declared for the user")
    private Set<Skill> skillSet = new HashSet<>();

    @Schema(description = "Collection of interests for discovery and recommendations")
    private Set<Interest> interests = new HashSet<>();

    @NotNull
    @Schema(description = "Seniority level of the profile", example = "MID")
    private Seniority seniority;
}
