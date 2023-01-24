package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postId,CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(Long postId);
    CommentDTO getCommentById(Long postId,Long commentId);
    CommentDTO updateComment(Long postId,Long commentId,CommentDTO requestCommentDTO);
    void deleteComment(Long postId, Long commentId);
}
