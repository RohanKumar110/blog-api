package dev.rohankumar.blog.service;

import dev.rohankumar.blog.domain.Category;
import dev.rohankumar.blog.domain.Post;
import dev.rohankumar.blog.domain.User;
import dev.rohankumar.blog.exception.CategoryNotFoundException;
import dev.rohankumar.blog.exception.PostNotFoundException;
import dev.rohankumar.blog.exception.UserNotFoundException;
import dev.rohankumar.blog.payload.PostDTO;
import dev.rohankumar.blog.payload.PostResponse;
import dev.rohankumar.blog.repository.CategoryRepository;
import dev.rohankumar.blog.repository.PostRepository;
import dev.rohankumar.blog.repository.UserRepository;
import dev.rohankumar.blog.service.interfaces.IFileService;
import dev.rohankumar.blog.service.interfaces.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static dev.rohankumar.blog.constants.ErrorConstant.*;

@Service
@Transactional
public class PostService implements IPostService {

    private final ModelMapper mapper;
    private final IFileService fileService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostService(ModelMapper mapper,
                       IFileService fileService,
                       UserRepository userRepository,
                       PostRepository postRepository,
                       CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostResponse find(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort;
        if (sortDir.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = this.postRepository.findAll(pageable);
        List<PostDTO> posts = page.getContent()
                .stream().map(this::mapToDTO)
                .toList();
        return PostResponse.builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .firstPage(page.isFirst())
                .lastPage(page.isLast())
                .hasNext(page.hasNext())
                .posts(posts)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    @Override
    public List<PostDTO> find(String title) {
        List<Post> posts = this.postRepository.searchByTitle("%"+title+"%");
        return posts.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findByCategory(Long id) {

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND, id)));
        return this.postRepository.findByCategory(category)
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findByUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
        return this.postRepository.findByUser(user)
                .stream().map(this::mapToDTO)
                .toList();
    }

    @Override
    public PostDTO find(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND, id)));
        return mapToDTO(post);
    }

    @Override
    public PostDTO create(Long userId, Long categoryId, PostDTO postDTO) {
        Post post = mapToPost(postDTO);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, userId)));
        Category category = this.categoryRepository.findById(userId)
                .orElseThrow(() -> new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND, categoryId)));
        post.setUser(user);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = this.postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public PostDTO update(Long id, PostDTO postDTO) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND, id)));
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setImageName(post.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void delete(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND, id)));
        this.postRepository.delete(post);
    }

    @Override
    public PostDTO uploadPostImage(Long id,MultipartFile file) throws IOException {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND, id)));
        String fileName = this.fileService.saveFile(file);
        if(fileName != null){
            post.setImageName(fileName);
            Post updatedPost = this.postRepository.save(post);
            return mapToDTO(updatedPost);
        }
        return null;
    }

    @Override
    public byte[] getPostImage(String fileName) throws IOException {
        return this.fileService.getFile(fileName);
    }

    private Post mapToPost(PostDTO postDTO) {
        return mapper.map(postDTO, Post.class);
    }

    private PostDTO mapToDTO(Post post) {
        return mapper.map(post, PostDTO.class);
    }
}
