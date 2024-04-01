package org.billy.resortmanagementsystem.repositories;

import org.billy.resortmanagementsystem.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
