package org.billy.resortmanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonBackReference
	@JoinColumn
	@ManyToOne
	private Customer customer;

	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Item> items;

	@Embedded
	private AuditData auditData = new AuditData();

	private Status status;

	public Reservation(Customer customer, List<Item> list) {
		this.customer = customer;

		// Initialize the items list
		this.items = new ArrayList<>();
		if (items != null) {
			for (Item item : items) {
				addItem(item);
			}
		}
	}

	public void addItem(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		this.items.add(item);
		item.setOrder(this);
	}

}
