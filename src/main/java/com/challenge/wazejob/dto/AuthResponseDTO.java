package com.challenge.wazejob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response returned after successful authentication")
public class AuthResponseDTO {

    @Schema(description = "JWT token to authenticate future requests")
    private String token;

    @Schema(description = "Token expiration timestamp in epoch seconds", example = "1732051200")
    private long expiresAt;

    @Schema(description = "Minimal representation of the authenticated user")
    private UserResponseDTO user;
}
