package org.billy.resortmanagementsystem.services.security;//package miu.edu.resort.services.security;

import lombok.AllArgsConstructor;
//import miu.edu.resort.domain.User;
//import miu.edu.resort.repositories.UserRepository;
import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username); //check here later

        return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " doesn't exist"));
    }
    // test
}
