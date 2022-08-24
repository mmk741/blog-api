package com.blog.springboot.service.impl;

import com.blog.springboot.entity.Post;
import com.blog.springboot.exception.ResourceNotFoundException;
import com.blog.springboot.payload.PostDTO;
import com.blog.springboot.payload.PostResponse;
import com.blog.springboot.repository.PostRepository;
import com.blog.springboot.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO to entity
        Post post=mapToEntity(postDTO);


      Post newPost = postRepository.save(post);

       //converting entity to DTO
        PostDTO postResponse=mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);

       Page<Post> posts=  postRepository.findAll(pageable);

       //get content from page object
        List<Post> listOfPost= posts.getContent();

      List<PostDTO> content=  listOfPost .stream().map(post->mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;


    }

    @Override
    public PostDTO getPostById(long id) {

        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return mapToDTO(post);


    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //get post from db
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        Post updatedPost=postRepository.save(post);
        return  mapToDTO(updatedPost);

    }

    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        postRepository.delete(post) ;
    }


    //convert entity to DTO
    private PostDTO mapToDTO(Post post)
    {
        PostDTO postResponse=new PostDTO();
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setDescription(post.getDescription());
        postResponse.setId(post.getId());

        return postResponse;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDTO postDTO)
    {
        Post post=new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        return post;
    }


}
