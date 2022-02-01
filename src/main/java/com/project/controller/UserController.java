package com.project.controller;

import com.project.model.User;
import com.project.model.dto.ResponseDTO;
import com.project.model.dto.UserDTO;
import com.project.service.UserService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User userLogin = userService.findByUsernameAndPassword(username, password);
        if (userLogin != null) {
            if (userLogin.getPassword().equals(password))
                return new ResponseEntity<>(new ResponseDTO("user", userLogin.getFirstName() + userLogin.getLastName()), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
