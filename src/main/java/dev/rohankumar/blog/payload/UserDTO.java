package dev.rohankumar.blog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class UserDTO {

    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Incorrect Email Address")
    private String email;
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 6,max = 25,message = "Username should be between 6 to 25 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6,max = 25,message = "Password should be between 6 to 25 characters")
    private String password;

    public UserDTO(){

    }

    public UserDTO(Long id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
