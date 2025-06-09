package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        System.out.println("User id from session: " + userId);
        model.addAttribute("userId", userId);
    
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (!optionalUser.isPresent()) {
            return "redirect:/login";
        }
        
        User user = optionalUser.get();
        model.addAttribute("user", user);
        Optional<Profile> optionalProfile = profileRepository.findByUser(user);
    
        if (!optionalProfile.isPresent()) {
            return "redirect:/create_profile"; 
        }
        
        Profile profile = optionalProfile.get();
        
        model.addAttribute("profile", profile);
        return "view_profile";
    }
    
    @PostMapping("/update_profile")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("oldPassword") String oldPassword,
                                @RequestParam(value = "password", required = false) String newPassword,
                                @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                @RequestParam("profileDescription") String description,
                                @RequestParam("clientLink") String clientLink,
                                HttpSession session, Model model) {
    
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
    
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return "redirect:/login";
        }
        User user = optionalUser.get();
    
        Optional<Profile> optionalProfile = profileRepository.findByUser(user);
        if (!optionalProfile.isPresent()) {
            return "redirect:/create_profile?userId=" + userId;
        }
        Profile profile = optionalProfile.get();
    
        user.setEmail(email);
    
        if (newPassword != null && !newPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                user.setPassword(newPassword);
            } else {
                model.addAttribute("error", "New password and confirmation do not match");
                model.addAttribute("profile", profile);
                model.addAttribute("user", user);
                return "view_profile";
            }
        }
    
        userRepository.save(user);
    
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setDescription(description);
        profile.setClientLink(clientLink);
    
        profileRepository.save(profile);
    
        return "redirect:/view_profile";
    }
    @GetMapping("/clients")
public String showClients(Model model) {
    List<Profile> clientProfiles = profileRepository.findAll();
    model.addAttribute("clients", clientProfiles);
    return "client_profiles";
}

    

}
