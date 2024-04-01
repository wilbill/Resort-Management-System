package org.billy.resortmanagementsystem.services;

import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String requestUserName);

    void createUser(UserDTO signUpRequest);
}
