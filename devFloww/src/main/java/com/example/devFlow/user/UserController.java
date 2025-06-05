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
	
	

	@GetMapping
	public List<User> getUsers(){
		return userService.getUsers();
	}
	 @PostMapping("/check-email")
	    public String checkEmail(@RequestParam String email, Model model) {
	        if (userService.emailExists(email)) {
	            model.addAttribute("error", "Email already registered.");
	            return "signup";
	        } else {
	            model.addAttribute("email", email);
	            return "signup_info";
	        }
	    }
	 
	 @PostMapping("/check-username")
	    public String checkUsername(@RequestParam String username, Model model) {
	        if (userService.usernameExists(username)) {
	            model.addAttribute("error", "Username already registered.");
	            return "signup";
	        } else {
	            model.addAttribute("username", username);
	            return "successs";
	        }
	    }
}
