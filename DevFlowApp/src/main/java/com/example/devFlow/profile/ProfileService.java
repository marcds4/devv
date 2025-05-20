package com.example.devFlow.profile;

import com.example.devFlow.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public List<Profile> getProfiles() {
        return profileRepository.findAll();
    }

    public Profile createUserProfile(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setGender(request.gender());
        profile.setDob(request.dob());
        profile.setDescription(request.description());
        profile.setClientLink(request.clientLink());
        profile.setProfileImage(request.profileImage());
        return profileRepository.save(profile);
    }
}
