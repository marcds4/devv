package com.example.devFlow.login;

import com.example.devFlow.user.UserService;
import com.example.devFlow.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/custom_login")
public String showEmailForm(Model model) {
    model.addAttribute("loginRequest", new LoginRequest());
    return "login"; // returns login.html
}

    @PostMapping("/check-email-login")
    public String handleEmailSubmit(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);

        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            model.addAttribute("emailError", "Email not found. Please register.");
            model.addAttribute("loginRequest", loginRequest);
            return "login";
        }

        model.addAttribute("loginRequest", loginRequest);
        return "login_info";
    }

    @PostMapping("/check-password-login")
    public String handlePasswordSubmit(@ModelAttribute("loginRequest") LoginRequest loginRequest,
                                       Model model,
                                       HttpServletRequest request,
                                       HttpSession session) {
    
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
    
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            model.addAttribute("emailError", "User not found.");
            return "login";
        }
    
        User user = optionalUser.get();
    
        if (!userService.checkPassword(email, password)) {
            model.addAttribute("passwordError", "Incorrect password.");
            model.addAttribute("loginRequest", loginRequest);
            return "login_info";
        }
    
        // ✅ Correct: Set session attributes after successful login
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("userId", user.getId());
    
        return "redirect:/client_dashboard?userId=" + user.getId();
    }
    
}
