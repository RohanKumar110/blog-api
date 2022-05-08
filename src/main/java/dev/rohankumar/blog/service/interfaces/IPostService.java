package dev.rohankumar.blog.service.interfaces;

import dev.rohankumar.blog.payload.PostDTO;
import dev.rohankumar.blog.payload.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {

    PostResponse find(int pageNo, int pageSize,String sortBy,String sortDir);
    List<PostDTO> find(String title);
    List<PostDTO> findByCategory(Long id);
    List<PostDTO> findByUser(Long id);
    PostDTO find(Long id);
    PostDTO create(Long userId,Long categoryId,PostDTO postDTO);
    PostDTO update(Long id, PostDTO postDTO);
    void delete(Long id);
    PostDTO uploadPostImage(Long id,MultipartFile file) throws IOException;
    byte[] getPostImage(String fileName) throws IOException;
}
