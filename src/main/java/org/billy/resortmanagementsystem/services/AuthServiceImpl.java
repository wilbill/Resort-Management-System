package org.billy.resortmanagementsystem.services;//package miu.edu.resort.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
//import miu.edu.resort.domain.User;
//import miu.edu.resort.dto.UserDTO;
//import miu.edu.resort.services.security.CustomUserDetails;
//import miu.edu.resort.services.security.JwtUtil;
//import miu.edu.resort.web.dto.LoginRequest;
//import miu.edu.resort.web.dto.LoginResponse;
//import miu.edu.resort.web.dto.SignupRequest;
import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.dto.UserDTO;
import org.billy.resortmanagementsystem.services.AuthService;
import org.billy.resortmanagementsystem.services.UserService;
import org.billy.resortmanagementsystem.services.security.CustomUserDetails;
import org.billy.resortmanagementsystem.services.security.JwtUtil;
import org.billy.resortmanagementsystem.web.dto.LoginRequest;
import org.billy.resortmanagementsystem.web.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public LoginResponse signUp(UserDTO signUpRequest) throws Exception {
        String requestUserName = signUpRequest.getUserName();
        Optional<User> user = userService.findByUsername(requestUserName);

        if (user.isPresent()) {
            throw new Exception("username already exists");
        }

        String plainPassword = signUpRequest.getUserPass();
        signUpRequest.setUserPass(bCryptPasswordEncoder.encode(plainPassword));

        userService.createUser(signUpRequest);

        return this.login(new LoginRequest(signUpRequest.getUserName(), plainPassword));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPass()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        final String accessToken = JwtUtil.generateToken(userDetails);

        return new LoginResponse(userDetails.getUsername(), accessToken);
    }
}
