package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.PostDTO;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    // Create blog post API
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //Get All Posts
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
//        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.ACCEPTED);
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // Get Post by Id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update Post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable(name="id") Long id){
        return ResponseEntity.ok(postService.updatePost(postDTO,id));
    }

    // Delete Post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post Delete Successfully");
    }
}
