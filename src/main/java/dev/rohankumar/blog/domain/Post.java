package dev.rohankumar.blog.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String title;
    @Column(nullable = false,length = 10000)
    private String body;
    private String imageUrl;
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
}