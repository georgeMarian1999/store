package com.project.service;

import com.project.model.Role;
import com.project.model.User;
import com.project.repo.RoleRepository;
import com.project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


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

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
