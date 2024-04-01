package org.billy.resortmanagementsystem.services;

import org.billy.resortmanagementsystem.dto.UserDTO;
import org.billy.resortmanagementsystem.web.dto.LoginRequest;
import org.billy.resortmanagementsystem.web.dto.LoginResponse;

public interface AuthService {
    LoginResponse signUp(UserDTO signUpRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
