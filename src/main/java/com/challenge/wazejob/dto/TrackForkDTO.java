package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Optional payload to customize a forked track")
public class TrackForkDTO {

    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Schema(description = "New title for the forked track", example = "Backend Java Specialist - Custom")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    @Schema(description = "New description for the forked track", example = "Versão personalizada para os alunos FIAP")
    private String description;
}

