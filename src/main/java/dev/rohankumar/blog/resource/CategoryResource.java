package dev.rohankumar.blog.resource;

import dev.rohankumar.blog.payload.CategoryDTO;
import dev.rohankumar.blog.payload.HttpResponse;
import dev.rohankumar.blog.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static dev.rohankumar.blog.constants.MessageConstant.CATEGORY_DELETED_MSG;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryResource {

    private ICategoryService categoryService;

    @Autowired
    public CategoryResource(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll(){
        List<CategoryDTO> categories = this.categoryService.find();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id){
        CategoryDTO category = this.categoryService.find(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = this.categoryService.create(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updatedCategory = this.categoryService.update(id,categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        this.categoryService.delete(id);
        HttpResponse response = new HttpResponse(HttpStatus.NO_CONTENT.value(),String.format(CATEGORY_DELETED_MSG,id),true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
