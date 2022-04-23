package dev.rohankumar.blog.service;

import dev.rohankumar.blog.domain.Category;
import dev.rohankumar.blog.exception.CategoryNotFoundException;
import dev.rohankumar.blog.payload.CategoryDTO;
import dev.rohankumar.blog.repository.CategoryRepository;
import dev.rohankumar.blog.service.interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ModelMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> find() {
        return this.categoryRepository.findAll()
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public CategoryDTO find(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "+id));
        return mapToDTO(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = mapToCategory(categoryDTO);
        Category savedCategory = this.categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category foundCategory = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "+id));
        foundCategory.setTitle(categoryDTO.getTitle());
        foundCategory.setDescription(categoryDTO.getDescription());
        Category updatedCategory = this.categoryRepository.save(foundCategory);
        return mapToDTO(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        Category foundCategory = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "+id));
        this.categoryRepository.delete(foundCategory);
    }

    private CategoryDTO mapToDTO(Category category) {
        return this.mapper.map(category,CategoryDTO.class);
    }

    private Category mapToCategory(CategoryDTO categoryDTO){
        return mapper.map(categoryDTO,Category.class);
    }
}