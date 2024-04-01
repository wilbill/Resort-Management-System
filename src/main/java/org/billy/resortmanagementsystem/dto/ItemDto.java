package org.billy.resortmanagementsystem.dto;

import lombok.Data;
import org.billy.resortmanagementsystem.domain.AuditData;
import org.billy.resortmanagementsystem.domain.Product;
import org.billy.resortmanagementsystem.domain.Reservation;

import java.time.LocalDate;

@Data
public class ItemDto {
    private Integer occupants;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Reservation order;
    private Product product;
    private AuditData auditData;
}
