package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileController(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String createProfile(@RequestParam("userId") Long userId,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("gender") String gender,
                                @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                                @RequestParam("description") String description,
                                @RequestParam("clientLink") String clientLink) {

        User user = userRepository.findById(userId).orElseThrow();

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setGender(gender);
        profile.setDob(dob);
        profile.setDescription(description);
        profile.setClientLink(clientLink);
        profile.setProfileImage("/images/profile.jpg");

        profileRepository.save(profile);

        return "redirect:/success";
    }

    @GetMapping("/create_profile")
    public String showProfileForm(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "create_profile";
    }
}
