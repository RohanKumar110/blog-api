package dev.rohankumar.blog.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true,length = 50)
    private String username;
    @Column(nullable = false,length = 50)
    private String password;
    private LocalDateTime joinDate;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Post> posts;
}
