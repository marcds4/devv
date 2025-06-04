package com.example.devFlow.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    public ProjectController(ProjectService projectService) {
    	this.projectService=projectService;
    }

    @PostMapping("/create_project")
    public String createProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("isPrivate") boolean isPrivate,
            @RequestParam("showDevPrice") boolean showDevPrice,
            @RequestParam("category") ProjectCategory category,
            @RequestParam("subcategory") ProjectSubcategory subcategory,
            @RequestParam("paymentMethod") PaymentMethod paymentMethod,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam("estimatedDuration") EstimatedDuration estimatedDuration,
            @RequestParam("offerDurationDays") int offerDurationDays,
            @RequestParam("suggestedTechnologies") List<String> suggestedTechnologies
    ) {
        ProjectRequest request = new ProjectRequest(
                title,
                description,
                isPrivate,
                showDevPrice,
                category,
                subcategory,
                paymentMethod,
                maxPrice,
                estimatedDuration,
                offerDurationDays,
                suggestedTechnologies
        );

        Project savedProject = projectService.saveProject(request);
        return "redirect:/success";
    }
}
