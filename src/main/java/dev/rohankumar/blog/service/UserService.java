package dev.rohankumar.blog.service;

import dev.rohankumar.blog.domain.User;
import dev.rohankumar.blog.exception.UserNotFoundException;
import dev.rohankumar.blog.payload.UserDTO;
import dev.rohankumar.blog.repository.UserRepository;
import dev.rohankumar.blog.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> find() {
        return this.userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public UserDTO find(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return mapToDTO(user);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        user.setJoinDate(LocalDateTime.now());
        User savedUser = this.userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User foundUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        foundUser.setName(userDTO.getName());
        foundUser.setEmail(userDTO.getEmail());
        foundUser.setUsername(userDTO.getUsername());
        foundUser.setPassword(userDTO.getPassword());
        User updatedUser = this.userRepository.save(foundUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User foundUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        this.userRepository.delete(foundUser);
    }

    private UserDTO mapToDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    private User mapToUser(UserDTO userDTO) {

        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }
}