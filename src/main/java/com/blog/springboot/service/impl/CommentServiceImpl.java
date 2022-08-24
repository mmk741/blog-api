package com.blog.springboot.service.impl;

import com.blog.springboot.entity.Comment;
import com.blog.springboot.entity.Post;
import com.blog.springboot.exception.BlogAPIException;
import com.blog.springboot.exception.ResourceNotFoundException;
import com.blog.springboot.payload.CommentDTO;
import com.blog.springboot.repository.CommentRepository;
import com.blog.springboot.repository.PostRepository;
import com.blog.springboot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment=mapToEntity(commentDTO);

        //retrive post  by post ID
         Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        //saving post to comment entity
         comment.setPost(post);

        Comment newComment= commentRepository.save(comment);

        return  mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {

        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment =commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

   if(!comment.getPost().getId().equals(post.getId()))
       throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment do not belong to this post");

   return mapToDTO(comment);



    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment =commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment do not belong to this post");

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

       Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment =commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment do not belong to this post");

        commentRepository.delete(comment);
    }


    private CommentDTO mapToDTO(Comment comment)
    {
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());

        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO)
    {
        Comment comment=new Comment();
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setId(commentDTO.getId());


        return comment;
    }
}
