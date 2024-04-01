package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; //added the maven dependency from here
import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String line1;

	private String line2;

	private String city;

	private String postalCode;

  @NotNull
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	private State state;

	@Embedded
	private AuditData auditData = new AuditData();

	@NotNull
	@Enumerated(EnumType.STRING)
	private AddressType type;
}
