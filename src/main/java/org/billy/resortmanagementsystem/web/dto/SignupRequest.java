package org.billy.resortmanagementsystem.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String userName;

    private String userPass;

    private Boolean active;

    private Boolean admin;
}
