package com.challenge.wazejob.repositories;

import com.challenge.wazejob.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {
}

