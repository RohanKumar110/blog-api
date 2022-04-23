package dev.rohankumar.blog.service.interfaces;

import dev.rohankumar.blog.payload.PostDTO;

import java.util.List;

public interface IPostService {

    List<PostDTO> find();
    List<PostDTO> find(String name);
    List<PostDTO> findByCategory(Long id);
    List<PostDTO> findByUser(Long id);
    PostDTO find(Long id);
    PostDTO create(Long userId,Long categoryId,PostDTO postDTO);
    PostDTO update(Long id, PostDTO postDTO);
    void delete(Long id);
}
