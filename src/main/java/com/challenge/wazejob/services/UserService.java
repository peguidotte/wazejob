package com.challenge.wazejob.services;

import com.challenge.wazejob.dto.AuthResponseDTO;
import com.challenge.wazejob.dto.UserCreateDTO;
import com.challenge.wazejob.dto.UserResponseDTO;
import com.challenge.wazejob.dto.UserUpdateDTO;
import com.challenge.wazejob.entities.User;
import com.challenge.wazejob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Transactional
    public AuthResponseDTO createUser(UserCreateDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponseDTO userResponse = convertToResponseDTO(savedUser);
        Instant issuedAt = Instant.now();
        String token = jwtTokenService.generateToken(savedUser.getEmail(), issuedAt);
        long expiresAt = jwtTokenService.calculateExpirationEpochSeconds(issuedAt);

        return new AuthResponseDTO(token, expiresAt, userResponse);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return convertToResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(String id, UserUpdateDTO dto) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
            }
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        if (!userRepository.existsById(uuid)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(uuid);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId().toString());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
