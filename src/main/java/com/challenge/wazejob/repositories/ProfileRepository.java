package com.challenge.wazejob.repositories;

import com.challenge.wazejob.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUser_UserId(UUID userId);

    boolean existsByUser_UserId(UUID userId);
}

