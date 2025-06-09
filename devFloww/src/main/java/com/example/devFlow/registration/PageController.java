package com.example.devFlow.registration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.devFlow.offer.Offer;
import com.example.devFlow.offer.OfferRepository;
import com.example.devFlow.project.Project;
import com.example.devFlow.project.ProjectRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    private final ProjectRepository projectRepository;
    private final OfferRepository offerRepository;
    public PageController(ProjectRepository projectRepository,OfferRepository offerRepository){
        this.projectRepository = projectRepository;
        this.offerRepository=offerRepository;
    }
    @GetMapping("/")
    public String getIndex(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @GetMapping("/custom_logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("✅ Session invalidated.");
        return "index"; // δείχνει logout.html
    }

    

    @GetMapping("/register")
    public String showSignupEmailForm(Model model) {
        model.addAttribute("registrationRequestEmail", new RegistrationRequestEmail(""));
        return "signup";
    }
    
    @GetMapping("/signup_info")
    public String showSignupUsernameForm(@RequestParam String email, Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest("", "", "", email));
        return "signup_info";
    }


    @GetMapping("/success")
    public String success() {
        return "success";
    }
    @PostMapping("/success")
public String successPost() {
    return "success";
}

@GetMapping("/notifications")
public String notifications(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return "redirect:/login";
    }

    // Fetch all projects posted by this user (client)
    List<Project> userProjects = projectRepository.findByUserId(userId);

    // Extract project IDs
    List<Long> projectIds = userProjects.stream()
                                       .map(Project::getId)
                                       .collect(Collectors.toList());

    // Find offers for those projects
    List<Offer> offers = offerRepository.findByProjectIdIn(projectIds);

    model.addAttribute("offers", offers);

    return "notifications";  // your notifications.html thymeleaf template
}


    
}



