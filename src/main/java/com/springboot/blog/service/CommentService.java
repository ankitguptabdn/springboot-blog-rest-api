package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId,long id);

    CommentDto updateCommentById(CommentDto commentDto,long postId,long commentId);

    void deleteById(long postId,long id);
}
