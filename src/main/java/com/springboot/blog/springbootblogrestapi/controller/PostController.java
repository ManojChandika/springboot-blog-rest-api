package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.PostDTO;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.PostConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue = PostConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = PostConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PostConstants.DEFAULT_SORT_BY,required = false ) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PostConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
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
