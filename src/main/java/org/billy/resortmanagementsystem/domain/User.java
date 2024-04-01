package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 2)
	private String userName;

	@JsonIgnore
	@NotNull
	@Size(min = 4)
	private String userPass;

	private Boolean active;

	@Transient
	private boolean admin;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable
	private List<Address> addresses;

	@Embedded
	private AuditData auditData = new AuditData();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, CascadeType.PERSIST })
	@JoinTable(name = "user_and_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	List<Role> roles;

	public User(String userName, String userPass, Boolean active, boolean admin) {
		this.userPass = userPass;
		this.userName = userName;
		this.active = active;
		this.admin = admin;
	}

}
