package org.billy.resortmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@Data
@JsonTypeName("ADMIN")
public class AdminDTO extends UserDTO {
}