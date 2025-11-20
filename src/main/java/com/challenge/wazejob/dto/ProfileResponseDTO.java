package com.challenge.wazejob.dto;

import com.challenge.wazejob.enums.Interest;
import com.challenge.wazejob.enums.Seniority;
import com.challenge.wazejob.enums.Skill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(description = "Profile information returned through the API")
public class ProfileResponseDTO {

    @Schema(description = "Profile identifier", example = "a4ad087a-48e2-4ddb-91e7-6ef3a8bbcae0")
    private String profileId;

    @Schema(description = "Associated user identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @Schema(description = "GitHub URL recorded for the profile")
    private String github;

    @Schema(description = "Optional biography")
    private String bio;

    @Schema(description = "Declared skills")
    private Set<Skill> skillSet;

    @Schema(description = "Declared interests")
    private Set<Interest> interests;

    @Schema(description = "Seniority level")
    private Seniority seniority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

