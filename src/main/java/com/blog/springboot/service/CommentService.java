package com.blog.springboot.service;

import com.blog.springboot.entity.Comment;
import com.blog.springboot.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);

    CommentDTO getCommentById(Long postId,Long commentId);

    CommentDTO updateComment(Long postId,Long commentId,CommentDTO commentDTO);

    void deleteComment(Long postId , Long commentId);


}
