package dev.rohankumar.blog.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3,max = 100,message = "Title should be greater than or equal to 3 characters")
    private String title;
    @NotEmpty(message = "Body cannot be empty")
    @Size(min = 10,message = "Body should be greater than or equal to 10 characters")
    private String body;
    private String imageName;
    private LocalDateTime createdAt;
    private CategoryDTO category;
    private UserDTO user;
}
