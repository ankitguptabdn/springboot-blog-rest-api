package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2,message = "post tittle should be atleast 2 char")
    private String title;
    @NotEmpty
    @Size(min=10,message = "post description should have atleast 10 char")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}