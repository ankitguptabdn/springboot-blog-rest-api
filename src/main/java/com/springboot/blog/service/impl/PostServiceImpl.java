package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
@Autowired
public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {

    this.postRepository = postRepository;
    this.mapper = mapper;
}
    private PostRepository postRepository;
    private ModelMapper mapper;
    @Override
    public PostDto createPost(PostDto postDto) {
        //convert dto to entity
        Post post = mapDtoToEntity(postDto);
        Post newPost = postRepository.save(post);
        //convert entity to dto
        PostDto postResponse = mapEntitytoDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String SortBy,String SortDir) {
        //List<Post> posts =postRepository.findAll();
       // return posts.stream().map(post -> mapEntitytoDto(post)).collect(Collectors.toList());

        // Sorting the post by asc and descending
       Sort sort = SortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(SortBy).ascending()
               :Sort.by(SortBy).descending();
       // Pageable pageable = PageRequest.of(pageNo,pageSize);
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts =postRepository.findAll(pageable);
        // page class has getcontent method, so we need content from page object
        List<Post> listofpost =posts.getContent();
        List<PostDto> content = listofpost.stream().map(post -> mapEntitytoDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapEntitytoDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        return mapEntitytoDto(updatePost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }


    //convert entity to dto
    private PostDto mapEntitytoDto(Post post){
        PostDto postDto = mapper.map(post,PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setContent(post.getContent());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
        return postDto;
    }
    //convert dto to entity
    private Post mapDtoToEntity(PostDto postDto){
        Post post = mapper.map(postDto,Post.class);
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
        return post;
    }

}
