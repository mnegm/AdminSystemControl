package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

}
