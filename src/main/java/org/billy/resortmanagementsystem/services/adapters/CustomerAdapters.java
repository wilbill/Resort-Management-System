package org.billy.resortmanagementsystem.services.adapters;//package miu.edu.resort.services.adapters;
//
//import miu.edu.resort.domain.Customer;
//import miu.edu.resort.dto.CustomerDTO;

import org.billy.resortmanagementsystem.domain.Customer;
import org.billy.resortmanagementsystem.dto.CustomerDTO;

public class CustomerAdapters {
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setUserName(customer.getUserName());
        customerDTO.setUserPass(customer.getUserPass());
        customerDTO.setAddresses(customer.getAddresses());
        customerDTO.setOrders(customer.getOrders());
        customerDTO.setActive(true);
        return customerDTO;
    }

    public static Customer fromDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setUserName(customerDTO.getUserName());
        customer.setUserPass(customerDTO.getUserPass());
        customer.setAddresses(customerDTO.getAddresses());
        customer.setActive(true);
        customer.setOrders(customerDTO.getOrders());

        return customer;
    }
}
