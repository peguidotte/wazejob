package com.challenge.wazejob.services;

import com.challenge.wazejob.dto.UserCreateDTO;
import com.challenge.wazejob.dto.UserResponseDTO;
import com.challenge.wazejob.dto.UserUpdateDTO;
import com.challenge.wazejob.entities.User;
import com.challenge.wazejob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }

        User user = new User();
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword()); // TODO: Criptografar senha com BCrypt
        user.setGithub(user.getGithub());

        User savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return convertToResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(String id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Atualiza apenas os campos não nulos
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            // Verifica se o novo email já existe (e não é o mesmo usuário)
            if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
            }
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword()); // TODO: Criptografar senha
        }
        if (dto.getGithub() != null) {
            user.setGithub(dto.getGithub());
        }
        // Role NÃO pode ser atualizada pelo cliente - apenas via endpoint admin

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    /**
     * Deleta um usuário
     */
    @Transactional
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Converte User Entity para UserResponseDTO (sem senha)
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setGithub(user.getGithub());
        return dto;
    }
}

