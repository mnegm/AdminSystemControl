package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/")
public class UsersController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@GetMapping("/login")   
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model) {
		userRepo.save(user);
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
