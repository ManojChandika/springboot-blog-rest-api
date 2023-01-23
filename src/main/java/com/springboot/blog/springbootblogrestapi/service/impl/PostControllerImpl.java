package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.dto.PostDTO;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repo.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostControllerImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    // Create Blog post rest API
    @Override
    public PostDTO createPost(PostDTO postDTO) {

        // Convert DTO to Entity
        Post post = mapToEntity(postDTO);
        Post newPost = postRepository.save(post);

        // Convert Entity to DTO and return
        return mapToDTO(post);
    }
    // Get all post rest API
    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize) {
        // Create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        // Get content from Page Object
        List<Post> listOfPost = posts.getContent();
        List<PostDTO> postDTO = listOfPost.stream().map(post ->mapToDTO(post) ).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDTO(postDTO);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }
    // Get Post by Id
    @Override
    public PostDTO getPostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    // Update Post By id
    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        Post postResponse = postRepository.save(post);
        return mapToDTO(postResponse);
    }

    // Delete Post
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    // Convert DTO into Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post= new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        return post;
    }

    // Convert Entity into DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setDescription(post.getDescription());
        return postDTO;
    }
}
