package com.example.devFlow.user;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getUsers(){
		return userService.getUsers();
	}
	 @PostMapping("/check-email")
	    public String checkEmail(@RequestParam String email, Model model) {
	        // Check if email already exists in the database
	        if (userService.emailExists(email)) {
	            // Email exists, show an error message or redirect to a login page
	            model.addAttribute("error", "Email already registered.");
	            return "signup";  // You can redirect to the login page if you prefer
	        } else {
	            // Email doesn't exist, prompt the user for additional info (like password)
	            model.addAttribute("email", email);  // pass the email to the next form
	            return "signup_info";  // Redirect to a new form where they can input additional information
	        }
	    }
	 
	 @PostMapping("/check-username")
	    public String checkUsername(@RequestParam String username, Model model) {
	        // Check if email already exists in the database
	        if (userService.usernameExists(username)) {
	            // Email exists, show an error message or redirect to a login page
	            model.addAttribute("error", "Username already registered.");
	            return "signup";  // You can redirect to the login page if you prefer
	        } else {
	            // Email doesn't exist, prompt the user for additional info (like password)
	            model.addAttribute("username", username);  // pass the email to the next form
	            return "successs";  // Redirect to a new form where they can input additional information
	        }
	    }
}
