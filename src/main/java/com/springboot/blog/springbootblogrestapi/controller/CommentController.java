package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.CommentDTO;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // Create Comment
    @PostMapping("{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable(value = "postId") long postId,
            @RequestBody CommentDTO commentDTO
    ){
        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    // Get All Comments by Post id
    @GetMapping("{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(
            @PathVariable(value = "postId") Long postId
    ){
      return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // Get Comment by id
    @GetMapping("{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId
    ){
        return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
    }

    //Update Comment
    @PutMapping("{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId,
            @RequestBody CommentDTO requestCommentDTO
    ){
        return ResponseEntity.ok(commentService.updateComment(postId,commentId,requestCommentDTO));
    }

    // Delete comment
    @DeleteMapping("{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
          @PathVariable(value = "postId") Long postId,
          @PathVariable(value = "id") Long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return ResponseEntity.ok("Comment Delete Successfully");
    }
}
