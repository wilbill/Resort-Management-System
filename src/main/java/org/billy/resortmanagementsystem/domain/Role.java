package org.billy.resortmanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private UserRoles role;

    @ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.ALL})
    @JsonIgnore
    Set<User> users = new HashSet<>();

    public Role(UserRoles role) {
        this.role = role;
    }
}