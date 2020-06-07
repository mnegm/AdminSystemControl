package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/")
public class UsersController {
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping("/")
	public String homePage() {
		return "home";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/login")   
	public String login() {
		return "login";
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody String req, Model model) {
		System.out.println(req);
		
		String[] str = req.split("&");
		String username;
		String password;
		
		username = str[1].split("=")[1];
		
		password = str[2].split("=")[1];

		
//		model.addAttribute("user", username);
//		model.addAttribute("pass", password);
		
		User user = new User(username , password , "ROLE_USER");
		userRepo.save(user);
		
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
