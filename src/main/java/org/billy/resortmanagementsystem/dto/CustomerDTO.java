package org.billy.resortmanagementsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
//import org.billy.resortmanagementsystem.domain.Address;
import org.billy.resortmanagementsystem.domain.Address;
import org.billy.resortmanagementsystem.domain.AuditData;
import org.billy.resortmanagementsystem.domain.Reservation;
import org.billy.resortmanagementsystem.dto.UserDTO;


@Data
@JsonTypeName("CUSTOMER")
public class CustomerDTO extends UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private List<Reservation> orders = new ArrayList<>();

    private AuditData auditData;

    private Integer id;

    private String userName;

    private String userPass;

    private Boolean active;

    private List<Address> addresses;
}
