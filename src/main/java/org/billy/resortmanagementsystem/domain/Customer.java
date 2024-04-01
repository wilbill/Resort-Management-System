package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
public class Customer extends User {
	@Size(min = 2)
	private String firstName;

	@Size(min = 2)
	private String lastName;

	@Column(unique = true)
	@Email
	@NotNull
	private String email;

	@JsonManagedReference
	@OneToMany(mappedBy = "customer")
	private List<Reservation> orders;

	@Embedded
	private AuditData auditData = new AuditData();

	public Customer(String userName, String userPass, Boolean active, boolean admin) {
		super(userName, userPass, active, admin);
	}

	public Customer() {
		super();
	}

	public Customer(String userName, String userPass, boolean active, boolean admin, String firstName, String lastName,
			String email) {
		super(userName, userPass, active, admin);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
}
