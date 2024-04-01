package org.billy.resortmanagementsystem.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.billy.resortmanagementsystem.domain.Customer;
import org.billy.resortmanagementsystem.domain.User;
import org.billy.resortmanagementsystem.dto.CustomerDTO;
import org.billy.resortmanagementsystem.repositories.CustomerRepository;
import org.billy.resortmanagementsystem.repositories.UserRepository;
import org.billy.resortmanagementsystem.services.CustomerService;
import org.billy.resortmanagementsystem.services.adapters.CustomerAdapters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerAdapters.fromDTO(customerDTO);
        Customer createCustomer = userRepository.save(customer);
        return CustomerAdapters.toDTO(createCustomer);
    }

    @Transactional
    public CustomerDTO getCustomerById(Integer customerId) throws Exception {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent())
            return CustomerAdapters.toDTO(customer.get());

        throw new Exception("Customer with id " + customerId + " not found");
    }

    @Transactional
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerAdapters::toDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO updateCustomer(Integer customerId, CustomerDTO updateCustomerDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findById(customerId);
        if (optionalUser.isPresent()) {
            Customer customer = CustomerAdapters.fromDTO(updateCustomerDTO);
            customer.setId(customerId);
            Customer savedCustomer = customerRepository.save(customer);
            return CustomerAdapters.toDTO(savedCustomer);
        }

        throw new Exception("Customer with id " + customerId + " not found");
    }

    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

}
