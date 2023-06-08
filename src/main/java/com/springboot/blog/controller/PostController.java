package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService)
    {
        this.postService = postService;
    }
    //Create blog Post
   @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                    @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // get posts by specific id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //Update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid@RequestBody PostDto postDto,@PathVariable(name ="id") long id){
       PostDto postResponse = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name ="id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Entity Deleted Sucessfully",HttpStatus.OK);
    }
}
