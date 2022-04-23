package dev.rohankumar.blog.resource;

import dev.rohankumar.blog.payload.PostDTO;
import dev.rohankumar.blog.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class PostResource {

    private final IPostService postService;

    @Autowired
    public PostResource(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> create(@PathVariable Long userId,
                                          @PathVariable Long categoryId,
                                          @Valid @RequestBody PostDTO postDTO) {
        PostDTO savedPost = this.postService.create(userId,categoryId,postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
}
