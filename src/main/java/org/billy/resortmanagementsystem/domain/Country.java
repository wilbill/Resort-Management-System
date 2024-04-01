package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import lombok.ToString.Exclude;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Country {

	@Id
	@Size(min = 1, max = 5)
	private String code;

	@Size(min = 2)
	private String name;
	@Min(0)
	private Integer population;

	@JsonIgnore
	@Exclude // Excluded because of circular dependency
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<State> states;

	@Embedded
	private AuditData auditData = new AuditData();
}
