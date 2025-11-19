package com.challenge.wazejob.controllers;

import com.challenge.wazejob.dto.TrackCreateDTO;
import com.challenge.wazejob.dto.TrackForkDTO;
import com.challenge.wazejob.dto.TrackResponseDTO;
import com.challenge.wazejob.dto.TrackUpdateDTO;
import com.challenge.wazejob.services.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/tracks")
@Tag(name = "Tracks", description = "Tracks represent curated learning journeys composed of checkpoints and steps.")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping
    @Operation(summary = "Create a new track", description = "Creates a brand-new track owned by a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Track created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<TrackResponseDTO> createTrack(@RequestBody @Valid TrackCreateDTO dto) {
        TrackResponseDTO created = trackService.createTrack(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getTrackId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/{id}/fork")
    @Operation(summary = "Fork an existing track", description = "Creates a new track derived from an existing one.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Track forked successfully"),
            @ApiResponse(responseCode = "404", description = "Original track not found")
    })
    public ResponseEntity<TrackResponseDTO> forkTrack(
            @Parameter(description = "Original track ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @RequestBody(required = false) @Valid TrackForkDTO dto) {
        TrackResponseDTO forked = trackService.forkTrack(id, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("api/v1/tracks/{id}")
                .buildAndExpand(forked.getTrackId())
                .toUri();
        return ResponseEntity.created(location).body(forked);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get track by ID", description = "Retrieves a track and its metadata by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Track found"),
            @ApiResponse(responseCode = "404", description = "Track not found")
    })
    public ResponseEntity<TrackResponseDTO> getTrackById(
            @Parameter(description = "Track ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        return ResponseEntity.ok(trackService.getTrack(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update track", description = "Applies partial updates to an existing track.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Track updated"),
            @ApiResponse(responseCode = "404", description = "Track not found"),
            @ApiResponse(responseCode = "400", description = "Invalid payload")
    })
    public ResponseEntity<TrackResponseDTO> updateTrack(
            @Parameter(description = "Track ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @RequestBody @Valid TrackUpdateDTO dto) {
        return ResponseEntity.ok(trackService.updateTrack(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete track", description = "Deletes a track. This does not delete forked copies.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Track deleted"),
            @ApiResponse(responseCode = "404", description = "Track not found")
    })
    public ResponseEntity<Void> deleteTrack(
            @Parameter(description = "Track ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        trackService.deleteTrack(id);
        return ResponseEntity.noContent().build();
    }
}
