package dev.rohankumar.blog.service.interfaces;

import dev.rohankumar.blog.payload.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    List<CategoryDTO> find();
    CategoryDTO find(Long id);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(Long id,CategoryDTO categoryDTO);
    void delete(Long id);
}
