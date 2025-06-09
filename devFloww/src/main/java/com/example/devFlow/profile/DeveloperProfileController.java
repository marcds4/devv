package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class DeveloperProfileController {
    

    private final DeveloperProfileRepository developerProfileRepository;
    private final UserRepository userRepository;

    public DeveloperProfileController(DeveloperProfileRepository developerProfileRepository, UserRepository userRepository) {
        this.developerProfileRepository = developerProfileRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create_profile_dev")
    public String showDeveloperProfileForm(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "create_profile_dev"; // your HTML form
    }

    @PostMapping("/create_dev")
    public String createDeveloperProfile(@RequestParam("userId") Long userId,
                                         @RequestParam("firstName") String firstName,
                                         @RequestParam("lastName") String lastName,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("description") String description,
                                         @RequestParam("skills") String skillsInput,
                                         @RequestParam("cvFile") MultipartFile cvFile) throws IOException {

        User user = userRepository.findById(userId).orElseThrow();

        DeveloperProfile profile = new DeveloperProfile();
        profile.setUser(user);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setGender(gender);
        profile.setDescription(description);

        if (!cvFile.isEmpty()) {
            String projectDir = System.getProperty("user.dir");
            String uploadDir = projectDir + File.separator + "cvs" + File.separator + userId;
        
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
        
            String filePath = uploadDir + File.separator + cvFile.getOriginalFilename();
            File dest = new File(filePath);
            cvFile.transferTo(dest);
            profile.setCvFileName(filePath);
        }

        // Convert skills (comma-separated)
        List<String> skills = Arrays.stream(skillsInput.split(","))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .toList();
        profile.setSkills(skills);

        developerProfileRepository.save(profile);

        return "redirect:/projects?userId=" + userId;
    }
    @GetMapping("/view_profile_dev")
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
        Optional<DeveloperProfile> optionalProfile = developerProfileRepository.findByUser(user);
    
        if (!optionalProfile.isPresent()) {
            // Handle profile not found, maybe redirect or show an error
            return "redirect:/create_profile_dev"; // or an appropriate page
        }
        
        DeveloperProfile profile = optionalProfile.get();
        
        // Pass the profile to the view
        model.addAttribute("profile", profile);
        return "view_profile_dev";
    }
    @PostMapping("/update_profile_dev")
public String updateDeveloperProfile(
        @RequestParam("email") String email,
        @RequestParam("username") String username,
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("description") String description,
        @RequestParam("skills") List<String> skills,
        @RequestParam(value = "cvFile", required = false) MultipartFile cvFile,
        HttpSession session, Model model) {

    // Retrieve userId from session
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return "redirect:/login"; // User not logged in
    }

    // Retrieve user and developer profile
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
        return "redirect:/login"; // User not found
    }
    User user = optionalUser.get();

    Optional<DeveloperProfile> optionalProfile = developerProfileRepository.findByUser(user);
    if (!optionalProfile.isPresent()) {
        // Handle if profile not found, maybe create one
        return "redirect:/create_profile_dev?userId=" + userId;
    }
    DeveloperProfile profile = optionalProfile.get();

    // Update user email and username
    user.setEmail(email);
    user.setUsername(username);
    userRepository.save(user);

    // Update profile fields
    profile.setFirstName(firstName);
    profile.setLastName(lastName);
    profile.setDescription(description);
    profile.setSkills(skills);

    // Handle CV file upload
    if (cvFile != null && !cvFile.isEmpty()) {
        try {
            String projectDir = System.getProperty("user.dir");
            String uploadDir = projectDir + File.separator + "cvs" + File.separator + userId;

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = cvFile.getOriginalFilename();
            String filePath = uploadDir + File.separator + originalFilename;
            File dest = new File(filePath);
            cvFile.transferTo(dest);

            // Store relative or absolute path as needed
            profile.setCvFileName(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file upload error, possibly add a message to the model
            model.addAttribute("error", "Failed to upload CV file.");
            return "view_profile_dev"; // or appropriate view
        }
    }

    // Save profile
    developerProfileRepository.save(profile);

    return "redirect:/view_profile_dev"; // redirect to profile view
}

}
