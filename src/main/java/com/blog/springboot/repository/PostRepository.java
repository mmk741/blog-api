package com.blog.springboot.repository;

import com.blog.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post,Long> {

}
