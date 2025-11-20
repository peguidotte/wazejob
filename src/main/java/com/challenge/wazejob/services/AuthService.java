package com.challenge.wazejob.services;

import com.challenge.wazejob.dto.AuthRequestDTO;
import com.challenge.wazejob.dto.AuthResponseDTO;
import com.challenge.wazejob.dto.UserResponseDTO;
import com.challenge.wazejob.entities.User;
import com.challenge.wazejob.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenService jwtTokenService,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Instant issuedAt = Instant.now();
            String token = jwtTokenService.generateToken(user.getEmail(), issuedAt);
            long expiresAt = jwtTokenService.calculateExpirationEpochSeconds(issuedAt);

            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    user.getUserId().toString(),
                    user.getName(),
                    user.getEmail()
            );

            return new AuthResponseDTO(token, expiresAt, userResponseDTO);
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
