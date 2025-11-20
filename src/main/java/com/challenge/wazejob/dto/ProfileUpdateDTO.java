package com.challenge.wazejob.dto;

import com.challenge.wazejob.enums.Interest;
import com.challenge.wazejob.enums.Seniority;
import com.challenge.wazejob.enums.Skill;
import com.challenge.wazejob.validation.GithubUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "DTO for updating an existing profile")
public class ProfileUpdateDTO {

    @GithubUrl
    @Schema(description = "GitHub URL to replace the current one", example = "https://github.com/peguidotte")
    private String github;

    @Size(max = 1000)
    @Schema(description = "Updated biography text")
    private String bio;

    @Schema(description = "Updated skills (replaces existing set)")
    private Set<Skill> skillSet;

    @Schema(description = "Updated interests (replaces existing set)")
    private Set<Interest> interests;

    @Schema(description = "Updated seniority level")
    private Seniority seniority;
}

