package org.billy.resortmanagementsystem.controller;//package miu.edu.resort.controller;

import java.util.List;
import java.util.Optional;

import org.billy.resortmanagementsystem.dto.CustomerDTO;
import org.billy.resortmanagementsystem.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/{customerID}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerID) throws Exception {
        CustomerDTO customer = customerService.getCustomerById(customerID);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/{customerID}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer customerID,
            @RequestBody CustomerDTO updateCustomersDTO) throws Exception {
        CustomerDTO customer = customerService.updateCustomer(customerID, updateCustomersDTO);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{customerID}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerID) {
        customerService.deleteCustomerById(customerID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
