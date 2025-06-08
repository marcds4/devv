package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperProfileService {

    private final DeveloperProfileRepository developerProfileRepository;
    private final UserRepository userRepository;

    public DeveloperProfileService(DeveloperProfileRepository developerProfileRepository,
                                    UserRepository userRepository) {
        this.developerProfileRepository = developerProfileRepository;
        this.userRepository = userRepository;
    }

    public List<DeveloperProfile> getProfiles() {
        return developerProfileRepository.findAll();
    }

    public DeveloperProfile createDeveloperProfile(Long userId, DeveloperProfileRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        DeveloperProfile profile = new DeveloperProfile();
        profile.setUser(user);
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setGender(request.gender());
        profile.setDescription(request.description());
        profile.setSkills(request.skills());  // Assign the skills list
        profile.setCvFileName(request.cvFileName());  // Optional
        profile.setProfileImage(request.profileImage());  // Default

        return developerProfileRepository.save(profile);
    }
}
