package dev.rohankumar.blog.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3,max = 100,message = "Title should be greater than or equal to 3 characters")
    private String title;
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10,message = "Description should be greater than or equal to 10 characters")
    private String description;
}
