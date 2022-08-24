package com.blog.springboot.service;

import com.blog.springboot.payload.PostDTO;
import com.blog.springboot.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);
   PostResponse getAllPost(int pageNo , int pageSize,String sortBy,String sortDir);

   PostDTO getPostById(long id);

   PostDTO updatePost(PostDTO postDTO,long id);
   void deletePostById(long id);

}
