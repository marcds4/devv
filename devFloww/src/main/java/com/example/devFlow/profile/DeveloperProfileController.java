package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
}
