package org.billy.resortmanagementsystem.web.dto;//package miu.edu.resort.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String userName;
    private String token;
}
