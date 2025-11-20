package com.challenge.wazejob.services;

import com.challenge.wazejob.dto.ProfileCreateDTO;
import com.challenge.wazejob.dto.ProfileResponseDTO;
import com.challenge.wazejob.dto.ProfileUpdateDTO;
import com.challenge.wazejob.entities.Profile;
import com.challenge.wazejob.entities.User;
import com.challenge.wazejob.repositories.ProfileRepository;
import com.challenge.wazejob.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProfileResponseDTO createProfile(ProfileCreateDTO dto) {
        User user = userRepository.findById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        if (profileRepository.existsByUser_UserId(user.getUserId())) {
            throw new IllegalStateException("Profile already exists for user: " + dto.getUserId());
        }

        Profile profile = new Profile();
        applyCreateData(profile, user, dto);

        return convertToResponseDTO(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileById(String profileId) {
        Profile profile = profileRepository.findById(UUID.fromString(profileId))
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with id: " + profileId));
        return convertToResponseDTO(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileByUserId(String userId) {
        Profile profile = profileRepository.findByUser_UserId(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("Profile not found for user id: " + userId));
        return convertToResponseDTO(profile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDTO> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfileResponseDTO updateProfile(String profileId, ProfileUpdateDTO dto) {
        Profile profile = profileRepository.findById(UUID.fromString(profileId))
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with id: " + profileId));

        if (dto.getGithub() != null) {
            profile.setGithub(dto.getGithub());
        }
        if (dto.getBio() != null) {
            profile.setBio(dto.getBio());
        }
        if (dto.getSkillSet() != null) {
            profile.setSkillSet(dto.getSkillSet());
        }
        if (dto.getInterests() != null) {
            profile.setInterests(dto.getInterests());
        }
        if (dto.getSeniority() != null) {
            profile.setSeniority(dto.getSeniority());
        }

        return convertToResponseDTO(profileRepository.save(profile));
    }

    @Transactional
    public void deleteProfile(String profileId) {
        UUID id = UUID.fromString(profileId);
        if (!profileRepository.existsById(id)) {
            throw new EntityNotFoundException("Profile not found with id: " + profileId);
        }
        profileRepository.deleteById(id);
    }

    private void applyCreateData(Profile profile, User user, ProfileCreateDTO dto) {
        profile.setUser(user);
        profile.setGithub(dto.getGithub());
        profile.setBio(dto.getBio());
        profile.setSkillSet(dto.getSkillSet());
        profile.setInterests(dto.getInterests());
        profile.setSeniority(dto.getSeniority());
    }

    private ProfileResponseDTO convertToResponseDTO(Profile profile) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setProfileId(profile.getProfileId().toString());
        dto.setUserId(profile.getUser().getUserId().toString());
        dto.setGithub(profile.getGithub());
        dto.setBio(profile.getBio());
        dto.setSkillSet(profile.getSkillSet());
        dto.setInterests(profile.getInterests());
        dto.setSeniority(profile.getSeniority());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        return dto;
    }
}
