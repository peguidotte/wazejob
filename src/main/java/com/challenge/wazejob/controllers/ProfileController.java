package com.challenge.wazejob.controllers;

import com.challenge.wazejob.services.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profiles")
@Tag(name = "Profiles", description = "Profiles represent user-specific information such as GitHub links and other relevant data.")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public void createProfile() {
        // Implementation for creating a profile will go here
    }

    @PatchMapping("/{id}")
    public void updateProfile() {
        // Implementation for updating a profile by ID will go here
    }

    @GetMapping("/{id}")
    public void getProfile() {
        // Implementation for retrieving a profile by ID will go here
    }
}
