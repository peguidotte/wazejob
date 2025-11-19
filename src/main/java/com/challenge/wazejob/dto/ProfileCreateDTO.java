package com.challenge.wazejob.dto;

import com.challenge.wazejob.validation.GithubUrl;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProfileCreateDTO {

    @GithubUrl
    @Schema(description = "User's GitHub profile URL", example = "https://github.com/peguidotte")
    private String github;
}
