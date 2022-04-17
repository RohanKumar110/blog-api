package dev.rohankumar.blog.resource;

import dev.rohankumar.blog.payload.HttpResponse;
import dev.rohankumar.blog.payload.UserDTO;
import dev.rohankumar.blog.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final IUserService userService;

    @Autowired
    public UserResource(IUserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> users = this.userService.find();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id){
        UserDTO user = this.userService.find(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        UserDTO savedUser = this.userService.create(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody UserDTO userDTO){
        UserDTO updatedUser = this.userService.update(id,userDTO);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        this.userService.delete(id);
        HttpResponse response = new HttpResponse(HttpStatus.NO_CONTENT.value(),"User Deleted with id: "+id,true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}