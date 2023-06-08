package com.springboot.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10,message = "body should not less than 10 char")
    private String body;
}
