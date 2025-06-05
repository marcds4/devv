package com.example.devFlow.project;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository,UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository=userRepository;
    }

    @PostMapping("/create_project")
    public String createProject(@RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "isPrivate", defaultValue = "false") boolean isPrivate,
            @RequestParam(value = "showDevPrice", defaultValue = "false") boolean showDevPrice,
            @RequestParam("category") ProjectCategory category,
            @RequestParam("subcategory") ProjectSubcategory subcategory,
            @RequestParam(value="paymentMethod", required = false) PaymentMethod paymentMethod,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam("estimatedDuration") EstimatedDuration estimatedDuration,
            @RequestParam("offerDurationDays") int offerDurationDays,
            @RequestParam(value = "suggestedTechnologies", required = false) List<String> suggestedTechnologies
    )  {
        User user = userRepository.findById(userId).orElseThrow();

        Project project = new Project();
        project.setUser(user);)
        project.setTitle(title);
        project.setDescription(description);
        project.setPrivate(isPrivate);
        project.setShowDevPrice(showDevPrice);
        project.setCategory(category);
        project.setSubcategory(subcategory);
        project.setPaymentMethod(paymentMethod);
        project.setMaxPrice(maxPrice);
        project.setEstimatedDuration(estimatedDuration);
        project.setOfferDurationDays(offerDurationDays);
        project.setSuggestedTechnologies(suggestedTechnologies);

        projectRepository.save(project);

        return "redirect:/success";
    }

    @GetMapping("/create_project")
    public String showProjectForm(@RequestParam(value = "userId", required = false) String userIdStr, Model model) {
        System.out.println("DEBUG: userIdStr = " + userIdStr);
        if (userIdStr == null || userIdStr.isBlank()) {
            throw new IllegalArgumentException("userId parameter is missing");
        }
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId: " + userIdStr);
        }

        // populate enums
        model.addAttribute("categories", ProjectCategory.values());
        model.addAttribute("subcategories", ProjectSubcategory.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("estimatedDurations", EstimatedDuration.values());
        model.addAttribute("userId", userId);
        return "success";
    }

}
