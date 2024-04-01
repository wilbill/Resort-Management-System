package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min = 1, max = 5)
	private String code;

	@Size(min = 2)
	private String name;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Country country;

	@Embedded
	private AuditData auditData = new AuditData();

}
