package org.billy.resortmanagementsystem.services.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.billy.resortmanagementsystem.domain.Role;
import org.billy.resortmanagementsystem.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String userName;

    @JsonIgnore
    private String password;

    private boolean active;

    private List<Role> roles;

    public CustomUserDetails(User user) {
        this.userName = user.getUserName();
        this.password = user.getUserPass();
        this.active = user.getActive();
        this.roles = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole().toString())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
