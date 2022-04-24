package dev.rohankumar.blog.payload;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean hasNext;
    private boolean lastPage;
    private boolean firstPage;
    private List<PostDTO> posts;
}
