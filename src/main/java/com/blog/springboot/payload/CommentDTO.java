package com.blog.springboot.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDTO {

    private Long id;
    private String name;
    private String email;
    private String body;
}
