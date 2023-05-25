package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
@Autowired
public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        //convert dto to entity
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());

        Post newPost = postRepository.save(post);

        //convert dto to entity
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setContent(newPost.getContent());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());

        return postResponse;
    }
}
