package org.billy.resortmanagementsystem.services;

import org.billy.resortmanagementsystem.dto.CustomerDTO;

import java.util.List;


public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomerById(Integer customerId) throws Exception;

    List<CustomerDTO> getAllCustomers();

    CustomerDTO updateCustomer(Integer customerId, CustomerDTO updateCustomerDTO) throws Exception;

    void deleteCustomerById(Integer id);
}
