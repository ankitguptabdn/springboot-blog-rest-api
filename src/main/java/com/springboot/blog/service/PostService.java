package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;
import java.util.ListResourceBundle;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(long id);
}
