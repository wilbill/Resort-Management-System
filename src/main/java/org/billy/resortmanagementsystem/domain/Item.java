package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer occupants;

	private LocalDate checkinDate;

	private LocalDate checkoutDate;
	@JoinColumn
	@ManyToOne
	private Product product;

	@JsonBackReference
	@JoinColumn(name = "order_id")
	@ManyToOne
	private Reservation order;
	@Embedded
	private AuditData auditData = new AuditData();

	public Item(Integer occupants, LocalDate checkinDate, LocalDate checkoutDate, Product product) {
		this.occupants = occupants;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.product = product;
	}

	public void setOrder(Reservation reservation) {
		this.order = reservation;
	}

}
