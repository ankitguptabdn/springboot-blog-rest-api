package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {
        Comment comment = mapDtoCom(commentDto);
        //retreive post by ID
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //comment entity to db
        Comment newcomment = commentRepository.save(comment);
        return mapComtoDto(newcomment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comment by postID
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert list of comment entity to list of comment dto entitiy
       return comments.stream().map(comment -> mapComtoDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
       //retreive post by ID
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //retreive comment by ID
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",id));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Content Doesn't Belongs to Post");
        }
        return mapComtoDto(comment);
    }

    @Override
    public CommentDto updateCommentById(CommentDto commentRequest, long postId, long commentId) {
        //retreive post by ID
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //retreive comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Content Doesn't Belongs to Post");
        }
            comment.setName(commentRequest.getName());
            comment.setBody(commentRequest.getBody());
            comment.setEmail(commentRequest.getEmail());
            //comment entity to db
        commentRepository.save(comment);
        Comment updatedcomment = commentRepository.save(comment);
        return mapComtoDto(updatedcomment);
    }

    @Override
    public void deleteById(long postId,long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //retreive comment by ID
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",id));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Content Doesn't Belongs to Post");
        }
        commentRepository.delete(comment);
    }

    //Convert entitiy to dto
    private CommentDto mapComtoDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    //Convert dto to entity

    private Comment mapDtoCom(CommentDto commentDto){
        Comment comment =new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
