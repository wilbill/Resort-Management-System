package org.billy.resortmanagementsystem.web;//package miu.edu.resort.web;

import lombok.AllArgsConstructor;
import org.billy.resortmanagementsystem.dto.UserDTO;
import org.billy.resortmanagementsystem.services.AuthService;
import org.billy.resortmanagementsystem.web.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private AuthService authService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(authService.signUp(userDTO));
    }
}
