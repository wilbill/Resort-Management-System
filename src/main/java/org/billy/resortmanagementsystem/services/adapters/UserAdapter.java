package org.billy.resortmanagementsystem.services.adapters;//package miu.edu.resort.services.adapters;
//
//import miu.edu.resort.domain.User;
//import miu.edu.resort.web.dto.SignupRequest;

import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.web.dto.SignupRequest;

public class UserAdapter {

    public static User getUserFromSignupRequest(SignupRequest request) {
        return new User(request.getUserName(), request.getUserPass(), request.getActive(), request.getAdmin());
    }
}
