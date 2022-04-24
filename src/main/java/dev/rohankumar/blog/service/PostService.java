package dev.rohankumar.blog.service;

import dev.rohankumar.blog.domain.Category;
import dev.rohankumar.blog.domain.Post;
import dev.rohankumar.blog.domain.User;
import dev.rohankumar.blog.exception.CategoryNotFoundException;
import dev.rohankumar.blog.exception.PostNotFoundException;
import dev.rohankumar.blog.exception.UserNotFoundException;
import dev.rohankumar.blog.payload.PostDTO;
import dev.rohankumar.blog.repository.CategoryRepository;
import dev.rohankumar.blog.repository.PostRepository;
import dev.rohankumar.blog.repository.UserRepository;
import dev.rohankumar.blog.service.interfaces.ICategoryService;
import dev.rohankumar.blog.service.interfaces.IPostService;
import dev.rohankumar.blog.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static dev.rohankumar.blog.constants.ErrorConstant.*;

@Service
@Transactional
public class PostService implements IPostService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostService(ModelMapper mapper,
                       UserRepository userRepository,
                       PostRepository postRepository,
                       CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<PostDTO> find() {
        return this.postRepository.findAll()
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PostDTO> find(String name) {
        return null;
    }

    @Override
    public List<PostDTO> findByCategory(Long id) {

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND,id)));
        return this.postRepository.findByCategory(category)
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findByUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND,id)));
        return this.postRepository.findByUser(user)
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public PostDTO find(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND,id)));
        return mapToDTO(post);
    }

    @Override
    public PostDTO create(Long userId, Long categoryId, PostDTO postDTO) {
        Post post = mapToPost(postDTO);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND,userId)));
        Category category = this.categoryRepository.findById(userId)
                .orElseThrow(() -> new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND,categoryId)));
        post.setUser(user);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = this.postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public PostDTO update(Long id, PostDTO postDTO) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND,id)));
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setImageUrl(post.getImageUrl());
        Post updatedPost = this.postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void delete(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND,id)));
        this.postRepository.delete(post);
    }

    private Post mapToPost(PostDTO postDTO) {
        return mapper.map(postDTO, Post.class);
    }

    private PostDTO mapToDTO(Post post) {
        return mapper.map(post, PostDTO.class);
    }
}
