package dev.rohankumar.blog.repository;

import dev.rohankumar.blog.domain.Category;
import dev.rohankumar.blog.domain.Post;
import dev.rohankumar.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    @Query("SELECT p from Post p where p.title LIKE :title")
    List<Post> searchByTitle(@Param("title") String title);
}
