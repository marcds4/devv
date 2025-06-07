package com.example.devFlow.registration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.devFlow.exception.UserAlreadyExistsException;
import com.example.devFlow.exception.UsernameTaken;
import com.example.devFlow.registration.event.RegistrationCompleteEvent;
import com.example.devFlow.registration.token.VerificationToken;
import com.example.devFlow.registration.token.VerificationTokenRepository;
import com.example.devFlow.user.User;
import com.example.devFlow.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, VerificationTokenRepository tokenRepository) {
        this.userService = userService;
        this.publisher = publisher;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/check-email")
    public String checkEmail(@ModelAttribute RegistrationRequestEmail registrationRequestEmail, Model model) {
        if (userService.findByEmail(registrationRequestEmail.email()).isPresent()) {
            model.addAttribute("error", "Email already registered.");
            model.addAttribute("registrationRequestEmail", registrationRequestEmail);
            return "signup";
        }

        model.addAttribute("registrationRequest", new RegistrationRequest("", "", "", registrationRequestEmail.email()));
        return "signup_info";
    }

    @PostMapping("/check-username")
    public String registerUserDetails(@ModelAttribute RegistrationRequest registrationRequest, Model model, final HttpServletRequest request) {
        try {
            User user = userService.registerUserDetails(registrationRequest);
            
            publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
            
            return "redirect:/success?success";
        } catch (UsernameTaken e) {
            model.addAttribute("error", e.getMessage());
            return "signup_info";
        }
    }

    @GetMapping("/signup_info")
    public String showSignupInfo(@RequestParam String email, Model model) {
        model.addAttribute("registrationRequestEmail", new RegistrationRequestEmail(email));
        return "signup_info";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()) {
            model.addAttribute("message", "Your account is already verified.");
            return "email_verification";
        }

        String result = userService.validateToken(token);
        if (result.equals("Valid")) {
            model.addAttribute("message", "Email verified successfully. You can now log in.");
        } else if (result.equals("Expired")) {
            model.addAttribute("message", "Verification link has expired.");
        } else {
            model.addAttribute("message", "Invalid verification token.");
        }

        return "email_verification";
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}