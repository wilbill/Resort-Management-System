package org.billy.resortmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.billy.resortmanagementsystem.domain.Customer;
import org.billy.resortmanagementsystem.domain.Item;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Customer customer;
    private List<Item> items;
}
