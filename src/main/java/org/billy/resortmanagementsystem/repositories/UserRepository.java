package org.billy.resortmanagementsystem.repositories;

import org.billy.resortmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
}
