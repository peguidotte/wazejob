package com.challenge.wazejob.services;

import com.challenge.wazejob.dto.TrackCreateDTO;
import com.challenge.wazejob.dto.TrackForkDTO;
import com.challenge.wazejob.dto.TrackResponseDTO;
import com.challenge.wazejob.dto.TrackUpdateDTO;
import com.challenge.wazejob.entities.Track;
import com.challenge.wazejob.entities.User;
import com.challenge.wazejob.repositories.TrackRepository;
import com.challenge.wazejob.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public TrackService(TrackRepository trackRepository, UserRepository userRepository) {
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TrackResponseDTO createTrack(TrackCreateDTO dto) {
        Track track = new Track();
        track.setUser(findUser(dto.getUserId()));
        track.setTitle(dto.getTitle());
        track.setDescription(dto.getDescription());
        track.setUserPrompt(dto.getUserPrompt());
        track.setAiPrompt(dto.getAiPrompt());
        track.setCompletedPercentage(0);
        Track saved = trackRepository.save(track);
        return convertToResponseDTO(saved);
    }

    @Transactional
    public TrackResponseDTO forkTrack(String trackId, TrackForkDTO dto) {
        Track original = findTrack(trackId);
        Track fork = new Track();
        fork.setUser(original.getUser());
        fork.setForkedFrom(original);
        fork.setTitle(dto != null && dto.getTitle() != null ? dto.getTitle() : original.getTitle());
        fork.setDescription(dto != null && dto.getDescription() != null ? dto.getDescription() : original.getDescription());
        fork.setUserPrompt(original.getUserPrompt());
        fork.setAiPrompt(original.getAiPrompt());
        fork.setCompletedPercentage(0);
        Track saved = trackRepository.save(fork);
        return convertToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public TrackResponseDTO getTrack(String id) {
        return convertToResponseDTO(findTrack(id));
    }

    @Transactional
    public TrackResponseDTO updateTrack(String id, TrackUpdateDTO dto) {
        Track track = findTrack(id);
        if (dto.getTitle() != null) {
            track.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            track.setDescription(dto.getDescription());
        }
        if (dto.getUserPrompt() != null) {
            track.setUserPrompt(dto.getUserPrompt());
        }
        if (dto.getAiPrompt() != null) {
            track.setAiPrompt(dto.getAiPrompt());
        }
        if (dto.getCompletedPercentage() != null) {
            track.setCompletedPercentage(dto.getCompletedPercentage());
        }
        Track saved = trackRepository.save(track);
        return convertToResponseDTO(saved);
    }

    @Transactional
    public void deleteTrack(String id) {
        Track track = findTrack(id);
        trackRepository.delete(track);
    }

    private Track findTrack(String id) {
        UUID uuid = UUID.fromString(id);
        return trackRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Track not found with id: " + id));
    }

    private User findUser(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private TrackResponseDTO convertToResponseDTO(Track track) {
        TrackResponseDTO dto = new TrackResponseDTO();
        dto.setTrackId(track.getTrackId().toString());
        dto.setUserId(track.getUser().getUserId().toString());
        dto.setForkedFromId(track.getForkedFrom() != null ? track.getForkedFrom().getTrackId().toString() : null);
        dto.setTitle(track.getTitle());
        dto.setDescription(track.getDescription());
        dto.setUserPrompt(track.getUserPrompt());
        dto.setAiPrompt(track.getAiPrompt());
        dto.setCompletedPercentage(track.getCompletedPercentage());
        dto.setCreatedAt(track.getCreatedAt() != null ? formatter.format(track.getCreatedAt()) : null);
        dto.setUpdatedAt(track.getUpdatedAt() != null ? formatter.format(track.getUpdatedAt()) : null);
        return dto;
    }
}
