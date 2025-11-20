package com.challenge.wazejob.controllers;

import com.challenge.wazejob.dto.ProfileCreateDTO;
import com.challenge.wazejob.dto.ProfileResponseDTO;
import com.challenge.wazejob.dto.ProfileUpdateDTO;
import com.challenge.wazejob.services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Profiles", description = "Profiles represent user-specific information such as GitHub links and other relevant data.")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    @Operation(summary = "Create profile", description = "Creates a profile for a given user (1:1 relation)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profile created"),
            @ApiResponse(responseCode = "400", description = "Validation error or profile already exists"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody @Valid ProfileCreateDTO dto) {
        ProfileResponseDTO created = profileService.createProfile(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getProfileId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    @Operation(summary = "List profiles")
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get profile by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<ProfileResponseDTO> getProfileById(
            @Parameter(description = "Profile ID", example = "a4ad087a-48e2-4ddb-91e7-6ef3a8bbcae0")
            @PathVariable String id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get profile by user id")
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(
            @Parameter(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update profile")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @PathVariable String id,
            @RequestBody @Valid ProfileUpdateDTO dto) {
        return ResponseEntity.ok(profileService.updateProfile(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete profile")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profile deleted"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<Void> deleteProfile(@PathVariable String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
