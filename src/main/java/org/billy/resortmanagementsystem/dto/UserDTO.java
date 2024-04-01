package org.billy.resortmanagementsystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.billy.resortmanagementsystem.domain.Address;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = false)
@JsonSubTypes({ @JsonSubTypes.Type(value = CustomerDTO.class, name = "CUSTOMER"),
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN") })
public class UserDTO {

    private Integer id;

    private String userName;

    private String userPass;

    private Boolean active;

    private List<Address> addresses;

}
