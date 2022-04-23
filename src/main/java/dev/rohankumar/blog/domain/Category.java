package dev.rohankumar.blog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String title;
    @Column(nullable = true,length = 255)
    private String description;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Post> posts;
}