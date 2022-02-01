package com.project.service;

import com.project.model.User;
import com.project.repo.CartRepository;
import com.project.repo.RoleRepository;
import com.project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartRepository cartRepository;

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User findByUsernameAndPassword(String username, String password) {
        try {
            User user = userRepository.findByUsernameAndPassword(username, password);
            if (user != null){
                return user;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
