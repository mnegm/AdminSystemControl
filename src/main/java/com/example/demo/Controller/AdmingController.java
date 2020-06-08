package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/admin")
public class AdmingController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("users")
	public String users(Model model) {
		List<User> users = userRepo.findAll();
		for(User user: users) {
			if(user.getRole()=="ROLE_ADMIN") {
				users.remove(user);
			}
		}	
		model.addAttribute("users", users);	
		return "users";
	}
	
	@PostMapping("users")
	public String userUpdate(@ModelAttribute("user") User user,Model model) {
		
		System.out.println(user);

		userRepo.save(user);
		
		List<User> users = userRepo.findAll();
		model.addAttribute("users", users);
		return "users";
	}
	
	@GetMapping("/users/{id}")
	public String user(@PathVariable int id,Model model) {
		User user = userRepo.getOne(id);
		model.addAttribute("user", user);
		return "userupdate";
	}
	
	@PostMapping("/users/{id}")
	public String deleteUser(@PathVariable int id,Model model) {
		userRepo.deleteById(id);
		List<User> users = userRepo.findAll();
		model.addAttribute("users", users);
		return "redirect:/admin/users";
	}
}
