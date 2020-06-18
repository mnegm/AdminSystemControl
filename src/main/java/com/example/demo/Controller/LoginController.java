package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.CommentRepo;
import com.example.demo.dao.PostRepo;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commRepo;
	
	@GetMapping("/test")
	public String testPage() {
		
		return "home";
	}
	
	@GetMapping("/")
	public String homePage(Authentication auth,Model model) {
		System.out.println(auth.getName());
		User user = userRepo.getUserByUsername(auth.getName());
		model.addAttribute("user" , user);
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
	public String authenticate(@ModelAttribute("user") User user) {
		System.out.println(user);
		userRepo.save(user);
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
