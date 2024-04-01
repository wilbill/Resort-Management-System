package org.billy.resortmanagementsystem.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.billy.resortmanagementsystem.domain.Role;
import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.domain.UserRoles;
import org.billy.resortmanagementsystem.dto.AdminDTO;
import org.billy.resortmanagementsystem.dto.CustomerDTO;
import org.billy.resortmanagementsystem.dto.UserDTO;
import org.billy.resortmanagementsystem.repositories.UserRepository;
import org.billy.resortmanagementsystem.services.UserService;
import org.billy.resortmanagementsystem.services.adapters.AdminAdapter;
import org.billy.resortmanagementsystem.services.adapters.CustomerAdapters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String requestUserName) {
        return userRepository.findByUserName(requestUserName);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user;
        Boolean isAdmin = false;
        if (userDTO.getClass().equals(AdminDTO.class)) {
            isAdmin = true;
            user = AdminAdapter.fromDTO((AdminDTO) userDTO);
        } else {
            user = CustomerAdapters.fromDTO((CustomerDTO) userDTO);
        }

        List<Role> roles = createRoles(isAdmin);
        user.setRoles(roles);

        for (Role role : roles) {
            role.getUsers().add(user);
        }

        userRepository.save(user);
    }

    private List<Role> createRoles(boolean admin) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(UserRoles.CUSTOMER));
        if (admin)
            roles.add(new Role(UserRoles.ADMIN));

        return roles;
    }
}
