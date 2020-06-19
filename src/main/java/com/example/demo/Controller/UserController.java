package com.example.demo.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PostRepo;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/user/{id}")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
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
		List<Post> friendsPosts = new ArrayList<Post>();
		for(User userFriend : user.getFriends()) {
			friendsPosts.addAll(userFriend.getPosts());
		}
		model.addAttribute("user", user);
		model.addAttribute("friends", user.getFriends());
		model.addAttribute("notFriends",notFriends);
		model.addAttribute("posts", user.getPosts());
		model.addAttribute("friendsPosts", friendsPosts);
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
	
	
	@GetMapping("/post")
	public String addPost(@RequestParam("post") String txt, @PathVariable int id) {
		
		Post post = new Post();
		post.setPost(txt);
		post.setUser(userRepo.getOne(id));
		postRepo.save(post);
		
		return "redirect:/user/{id}";
	}
	
	@GetMapping("/posts/{postId}")
	public String updatePost(@PathVariable int id , @PathVariable int postId, Model model) {
		Post post = postRepo.getOne(postId);
		model.addAttribute("post", post);
		model.addAttribute("user", userRepo.getOne(id));
		return "updatePost";
	}
	
	@PostMapping("/posts/{postId}")
	public String deletePost(@PathVariable int postId, @PathVariable int id) {
		postRepo.delete(postRepo.getOne(postId));
		return "redirect:/user/{id}";
	}
	
	
	@PostMapping("/posts/{postId}/update")
	public String postUpdate(@RequestParam("post") String txt , @PathVariable int id, @PathVariable int postId) {
		Post post = new Post();
		post.setPost(txt);
		post.setId(postId);
		post.setTime(LocalDateTime.now());
		post.setUser(userRepo.getOne(id));
		postRepo.save(post);
		
		
		return "redirect:/user/{id}";
	}
	
	
	
	
	
	
}
