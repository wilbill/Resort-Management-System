package org.billy.resortmanagementsystem.services.adapters;//package miu.edu.resort.services.adapters;
//
//import miu.edu.resort.domain.Admin;
//import miu.edu.resort.domain.Customer;
//import miu.edu.resort.dto.AdminDTO;
//import miu.edu.resort.dto.CustomerDTO;

import org.billy.resortmanagementsystem.domain.Admin;
import org.billy.resortmanagementsystem.dto.AdminDTO;

public class AdminAdapter {
    public static AdminDTO toDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setUserName(admin.getUserName());
        adminDTO.setUserPass(admin.getUserPass());
        adminDTO.setAddresses(admin.getAddresses());
        adminDTO.setActive(admin.getActive());
        return adminDTO;
    }

    public static Admin fromDTO(AdminDTO adminDTO) {
        Admin admin = new Admin();

        admin.setId(adminDTO.getId());
        admin.setUserName(adminDTO.getUserName());
        admin.setUserPass(adminDTO.getUserPass());
        admin.setAddresses(adminDTO.getAddresses());
        admin.setActive(adminDTO.getActive());

        return admin;
    }
}
