package dev.rohankumar.blog.service.interfaces;

import dev.rohankumar.blog.payload.UserDTO;
import java.util.List;

public interface IUserService {

    List<UserDTO> find();
    UserDTO find(Long id);
    UserDTO create(UserDTO userDTO);
    UserDTO update(Long id,UserDTO userDTO);
    void delete(Long id);
}
