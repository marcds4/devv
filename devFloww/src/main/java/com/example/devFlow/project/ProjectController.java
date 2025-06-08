package com.example.devFlow.project;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, ProjectService projectService) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create_project")
    public String createProject(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "isPrivate", defaultValue = "false") boolean isPrivate,
            @RequestParam(value = "showDevPrice", defaultValue = "false") boolean showDevPrice,
            @RequestParam("category") ProjectCategory category,
            @RequestParam("subcategory") ProjectSubcategory subcategory,
            @RequestParam(value = "paymentMethod", required = false) PaymentMethod paymentMethod,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam("estimatedDuration") EstimatedDuration estimatedDuration,
            @RequestParam("offerDurationDays") int offerDurationDays,
            @RequestParam(value = "suggestedTechnologies", required = false) List<String> suggestedTechnologies
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));

        Project project = new Project();
        project.setUser(user);
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

        return "redirect:/client_dashboard?userId=" + userId;
    }

    @GetMapping("/client_dashboard")
    public String showProfileForm(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "client_dashboard";
    }

    @GetMapping("/projects")
public String listProjects(
        @RequestParam(name = "query", required = false) String query,
        @RequestParam(name = "is_private", required = false) Integer isPrivateParam,
        Model model) {

    List<Project> projects;

    Boolean isPrivate = null;
    if (isPrivateParam != null) {
        isPrivate = (isPrivateParam == 1);
    }

    if (query != null && !query.isBlank()) {
        if (isPrivate != null) {
            projects = projectRepository.findByTitleContainingIgnoreCaseAndIsPrivate(query, isPrivate);
        } else {
            projects = projectRepository.findByTitleContainingIgnoreCase(query);
        }
    } else {
        if (isPrivate != null) {
            projects = projectRepository.findByIsPrivate(isPrivate);
        } else {
            projects = projectRepository.findAll();
        }
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    projects.forEach(project -> {
        if (project.getDatePosted() != null) {
            project.setFormattedDatePosted(project.getDatePosted().format(formatter));
        }
    });

    model.addAttribute("projects", projects);
    model.addAttribute("query", query);
    model.addAttribute("is_private", isPrivateParam);

    return "projects";
}


@GetMapping("/projects/{id}")
public String getProjectById(@PathVariable Long id, Model model) {
    System.out.println("Fetching project with id: " + id);
    var projectOpt = projectService.findById(id);
    if (projectOpt.isPresent()) {
        model.addAttribute("project", projectOpt.get());
        return "project";

    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
    }
}


}
