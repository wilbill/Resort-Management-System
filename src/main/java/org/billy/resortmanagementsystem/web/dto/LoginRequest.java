package org.billy.resortmanagementsystem.web.dto;//package miu.edu.resort.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String userPass;
}
