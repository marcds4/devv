package com.example.devFlow.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
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
    @GetMapping("/create_profile_dev")
    public String createProfileDev() {
        return "create_profile_dev";
    }

    
}



