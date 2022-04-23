package dev.rohankumar.blog.service;

import dev.rohankumar.blog.domain.User;
import dev.rohankumar.blog.exception.UserNotFoundException;
import dev.rohankumar.blog.payload.UserDTO;
import dev.rohankumar.blog.repository.UserRepository;
import dev.rohankumar.blog.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static dev.rohankumar.blog.constants.ErrorConstant.USER_NOT_FOUND;

@Service
@Transactional
public class UserService implements IUserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
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
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND,id)));
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

        return this.mapper.map(user,UserDTO.class);
    }

    private User mapToUser(UserDTO userDTO) {

       return this.mapper.map(userDTO,User.class);
    }
}