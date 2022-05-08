package dev.rohankumar.blog.resource;

import dev.rohankumar.blog.payload.HttpResponse;
import dev.rohankumar.blog.payload.PostDTO;
import dev.rohankumar.blog.payload.PostResponse;
import dev.rohankumar.blog.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static dev.rohankumar.blog.constants.MessageConstant.POST_DELETED_MSG;
import static dev.rohankumar.blog.constants.AppConstant.*;

@RestController
@RequestMapping("/api/v1/")
public class PostResource {

    private final IPostService postService;

    @Autowired
    public PostResource(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = this.postService.find(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{title}")
    public ResponseEntity<List<PostDTO>> search(
            @PathVariable String title) {
        List<PostDTO> posts = this.postService.find(title);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        PostDTO post = this.postService.find(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getByUser(@PathVariable Long userId) {
        List<PostDTO> posts = this.postService.findByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getByCategory(@PathVariable Long categoryId) {
        List<PostDTO> posts = this.postService.findByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> create(@PathVariable Long userId,
                                          @PathVariable Long categoryId,
                                          @Valid @RequestBody PostDTO postDTO) {
        PostDTO savedPost = this.postService.create(userId, categoryId, postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = this.postService.update(id, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id) {
        this.postService.delete(id);
        HttpResponse response = new HttpResponse(HttpStatus.NO_CONTENT.value(),String.format(POST_DELETED_MSG,id) , true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}