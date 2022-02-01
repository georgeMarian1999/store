package com.project.controller;

import com.project.model.Role;
import com.project.model.User;
import com.project.model.dto.ResponseDTO;
import com.project.model.dto.UserDTO;
import com.project.model.dto.UserLoginDTO;
import com.project.service.UserService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userService.findAllUsers()
                .forEach(user -> {
                    userDTOList.add(DTOUtils.userToDto(user));
                });
        if (userDTOList.size() > 0)
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        User userLogin = userService.findByUsernameAndPassword(username, password);
        if (userLogin != null) {
            if (userLogin.getPassword().equals(password)) {
                Optional<User> user = userService.findUserById(userLogin.getId());
                UserDTO userDTO = DTOUtils.userToDto(user.get());
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        Role role = userService.findRoleByName("user");
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getName(), role);
        userService.saveUser(user);
        UserDTO userDto = DTOUtils.userToDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
