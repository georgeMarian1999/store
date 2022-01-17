package com.project.utils;

import com.project.model.User;
import com.project.model.dto.UserDTO;

public class DTOUtils {
    public static UserDTO objToDto(User user) {
        UserDTO udto = new UserDTO();
        udto.setEmail(user.getEmail());

        udto.setFirstName(user.getFirstName());
        udto.setLastName(user.getLastName());
        udto.setUsername(user.getUsername());
        udto.setPassword(user.getPassword());
        if (user.getRole() != null) {
            udto.setRole(user.getRole()
                    .getName());
        }
        return udto;
    }
}
