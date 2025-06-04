package com.example.devFlow.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.devFlow.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    

    public LoginController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder(); 
    }

    
    
    @GetMapping("/login")
    public String showEmailForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        System.out.println("new");
        return "login";
    }

    @PostMapping("/check-email-login")
    public String handleEmailSubmit(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        System.out.println("Set email");
        if (!userService.findByEmail(email).isPresent()) {
            model.addAttribute("emailError", "Email not found. Please register.");
            model.addAttribute("loginRequest", loginRequest);
            return "login";
        }

        model.addAttribute("loginRequest", loginRequest);
        return "login_info";
    }
    
    @PostMapping("/check-password-login")
    public String handlePasswordSubmit(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        var optionalUser = userService.findByEmail(email);
        
        var user = optionalUser.get();
        System.out.println("Entered password: " + password);
        System.out.println("Stored password: " + user.getPassword());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("passwordError", "Incorrect password.");
            model.addAttribute("loginRequest", loginRequest);
            return "login_info";
        }



        return "login_success";
    }


}
