package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;


@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	private String username;
	
	private String password;
	
	private int enabled;
	
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Post> posts= new ArrayList<Post>();
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="friends",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id", nullable=false)},
			inverseJoinColumns = {@JoinColumn(name="friend_id", referencedColumnName = "id",nullable=false)})
	private List<User> friends=  new ArrayList<User>();

	public User(String username, String password, String role) {
		
		String salt = BCrypt.gensalt(12);
		password = BCrypt.hashpw(password, salt);
		role= "ROLE_"+role;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = 1;
	}

	public User() {
		this.enabled = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String salt = BCrypt.gensalt(12);
		password = BCrypt.hashpw(password, salt);
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [" + " username=" + username + ", password=" + password + 
				 ", role=" + role + "]";
	}
	
	public void addFriend(User friend) {

		friends.add(friend);
	}
	
	public void deleteFriend(User friend) {
	
		for(int i =0;i<friends.size();i++) {
			if(friend.id == friends.get(i).id) {
				friends.remove(i);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
