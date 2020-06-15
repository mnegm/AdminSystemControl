package com.example.demo.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/user/{id}")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String userPage(@PathVariable int id,Model model,Principal auth) {
		User user = userRepo.getOne(id);
		User authUser = userRepo.getUserByUsername(auth.getName());
		if(user.getId()!= authUser.getId()) {
			return "redirect:/access-denied";
		}
		List<User> all = userRepo.findAll();
		List<User> friends = new ArrayList<User>(user.getFriends());
		friends.add(user);
		List<User> notFriends = nonFriends(all, friends);

		model.addAttribute("user", user);
		model.addAttribute("friends", user.getFriends());
		model.addAttribute("notFriends",notFriends);
		return "user";
	}
	
	@PostMapping("/{friendId}")
	public String deleteId(@PathVariable("friendId") int friendId, @PathVariable int id) {
		userRepo.getOne(id).deleteFriend(userRepo.getOne(friendId));
		userRepo.getOne(friendId).deleteFriend(userRepo.getOne(id));
		userRepo.save(userRepo.getOne(id));
		userRepo.save(userRepo.getOne(friendId));
		return "redirect:/user/{id}";
	}
	
	@GetMapping("/{friendId}")
	public String addFriend(@PathVariable int friendId, @PathVariable int id ) {
		
		userRepo.getOne(id).addFriend(userRepo.getOne(friendId));
		userRepo.getOne(friendId).addFriend(userRepo.getOne(id));
		userRepo.save(userRepo.getOne(id));
		userRepo.save(userRepo.getOne(friendId));
		
		return "redirect:/user/{id}";
	}
	
	private List<User> nonFriends(List<User> all, List<User> friends){
		
		List<User> notFriends = new ArrayList<User>();
		for(User allUser: all) {
			int found = 0;
			for(User user: friends) {
				if( user == allUser) {
					found =1;
					break;
				}
			}
			if(found == 0) {
				notFriends.add(allUser);
			}
		}
		return notFriends;
	}
	
	
	
	
	
	
	
	
	
	
	
}
