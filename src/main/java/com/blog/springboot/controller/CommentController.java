package com.blog.springboot.controller;

import com.blog.springboot.payload.CommentDTO;
import com.blog.springboot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId ,@RequestBody CommentDTO commentDTO)
    {
return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);

    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentByPostId(@PathVariable(value = "postId") Long postId)
    {
        return commentService.getCommentByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable  Long postId ,@PathVariable Long commentId)
    {
          CommentDTO commentDTO= commentService.getCommentById(postId,commentId);

          return new ResponseEntity<>(commentDTO,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId,@PathVariable Long commentId,@RequestBody CommentDTO commentDTO)
    {
        CommentDTO commentDTO1 = commentService.updateComment(postId,commentId, commentDTO);

        return new  ResponseEntity<>(commentDTO1,HttpStatus.OK);

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable  Long postId ,@PathVariable Long commentId)
    {
        commentService.deleteComment(postId,commentId);

        return new ResponseEntity<>("comment with id :" +commentId+" deleted successfully",HttpStatus.OK);
    }

}
