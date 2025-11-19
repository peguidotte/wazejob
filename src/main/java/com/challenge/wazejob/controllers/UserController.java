package com.challenge.wazejob.controllers;

import com.challenge.wazejob.dto.UserCreateDTO;
import com.challenge.wazejob.dto.UserResponseDTO;
import com.challenge.wazejob.dto.UserUpdateDTO;
import com.challenge.wazejob.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO userDTO) {
        UserResponseDTO createdUser = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "User ID (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "User ID (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @RequestBody @Valid UserUpdateDTO userDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
