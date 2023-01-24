package com.springboot.blog.springbootblogrestapi.service.impl;



import com.springboot.blog.springbootblogrestapi.dto.CommentDTO;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogApiException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repo.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repo.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Create Comment
    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));
        Comment comment = mapToEntity(commentDTO);
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }
    // Get All Comments by Post Id
    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        // Get Comments by post id
        List<Comment> comments = commentRepository.findByPostId(postId);
        // Convert list of comment entities to comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    // Get Comment by post id
    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        // Check Post by id
        Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));
        // Check Comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }
        return mapToDto(comment);
    }

    // Update Comment
    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO requestCommentDTO) {
        // Check Post by id

        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        // Check Comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }
        comment.setName(requestCommentDTO.getName());
        comment.setEmail(requestCommentDTO.getEmail());
        comment.setBody(requestCommentDTO.getBody());

        return mapToDto(commentRepository.save(comment));
    }

    // Delete Comment
    @Override
    public void deleteComment(Long postId, Long commentId) {
        //Check post by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        // check comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }
        commentRepository.delete(comment);
    }

    // map DTO to Entity
    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO,Comment.class);
//        Comment comment= new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }

    // map Entity to DTO
    private CommentDTO mapToDto(Comment comment){
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
//        CommentDTO commentDTO= new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

}
