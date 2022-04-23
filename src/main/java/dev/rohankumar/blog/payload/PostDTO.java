package dev.rohankumar.blog.payload;

import dev.rohankumar.blog.domain.Category;
import dev.rohankumar.blog.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String title;
    private String body;
    private String imageUrl;
    private LocalDateTime createdAt;
    private CategoryDTO category;
    private UserDTO user;
}
