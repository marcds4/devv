package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

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

        return "redirect:/client_dashboard?userId=" + userId;
    }

    @GetMapping("/create_profile")
    public String showProfileForm(@RequestParam("userId") Long userId, Model model) {
        System.out.println("User id: "+userId);
        model.addAttribute("userId", userId);
        return "create_profile";
    }
    @GetMapping("/view_profile")
    public String viewProfile(HttpSession session, Model model) {
        // Retrieve userId from session
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            // Handle the case where userId is not in session (e.g., redirect to login)
            return "redirect:/login";
        }
        
        System.out.println("User id from session: " + userId);
        model.addAttribute("userId", userId);
    
        // Retrieve user from repository (assuming using Spring Data JPA)
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (!optionalUser.isPresent()) {
            // Handle user not found, e.g., redirect or show error page
            return "redirect:/login"; // or another appropriate page
        }
        
        User user = optionalUser.get();
        model.addAttribute("user", user);
        Optional<Profile> optionalProfile = profileRepository.findByUser(user);
    
        if (!optionalProfile.isPresent()) {
            // Handle profile not found, maybe redirect or show an error
            return "redirect:/create_profile"; // or an appropriate page
        }
        
        Profile profile = optionalProfile.get();
        
        // Pass the profile to the view
        model.addAttribute("profile", profile);
        return "view_profile";
    }
    

}
