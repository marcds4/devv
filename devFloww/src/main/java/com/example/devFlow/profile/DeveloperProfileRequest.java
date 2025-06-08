package com.example.devFlow.profile;

import java.time.LocalDate;
import java.util.List;

public record DeveloperProfileRequest(
        String firstName,
        String lastName,
        String gender,
        String description,
        String cvFileName,
        String profileImage,
        List<String> skills
) {}
