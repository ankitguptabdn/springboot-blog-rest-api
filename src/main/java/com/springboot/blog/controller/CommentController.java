package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
@PostMapping("/posts/{postId}/comments")
public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getComment(@PathVariable(value="postId")long postId){
        return commentService.getCommentsByPostId(postId);

    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId")long postId,
                                                     @PathVariable(name="id")long id){
        CommentDto commentDto =commentService.getCommentById(postId,id);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CommentDto> updateCommentById(@RequestBody CommentDto commentDto,
                                                         @PathVariable(value="postId")long postId,
                                                         @PathVariable(name="id")long commentId){
        CommentDto commentResponse=commentService.updateCommentById(commentDto,postId,commentId);
        return new ResponseEntity<>(commentResponse,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="postId")long postId,@PathVariable(name="id")long id){
        commentService.deleteById(postId,id);
        return new ResponseEntity<>("Comment Entity deleted sucessfully",HttpStatus.OK);
    }
}
