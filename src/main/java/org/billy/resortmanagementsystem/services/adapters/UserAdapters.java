package org.billy.resortmanagementsystem.services.adapters;//package miu.edu.resort.services.adapters;
//
//import miu.edu.resort.domain.User;
//import miu.edu.resort.dto.UserDTO;

import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.dto.UserDTO;

public class UserAdapters {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserPass(user.getUserPass());
        userDTO.setActive(user.getActive());
        userDTO.setAddresses(user.getAddresses());
        return userDTO;
    }

    public static User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setUserPass(userDTO.getUserPass());
        user.setActive(userDTO.getActive());
        user.setAddresses(userDTO.getAddresses());
        return user;
    }
}
