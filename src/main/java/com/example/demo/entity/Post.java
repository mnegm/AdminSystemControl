package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int id;
	
	@Column(name= "post")
	private String post;
	
	@Column(name= "time")
	private LocalDateTime time;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments = new ArrayList<Comment>();
	
	public Post() {
		this.time = LocalDateTime.now();
	}

	public Post(String post, User user) {
		this.post = post;
		this.user = user;
		this.time = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", post=" + post + ", time=" + time + ", user=" + user + ", comments=" + comments
				+ "]";
	}
	
	
}
