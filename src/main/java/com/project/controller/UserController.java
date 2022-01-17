package com.project.controller;

import com.project.model.dto.UserDTO;
import com.project.service.UserService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Iterable<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userService.findAllUsers()
                .forEach(user -> {
                    userDTOList.add(DTOUtils.objToDto(user));
                });
        return userDTOList;
    }
}
