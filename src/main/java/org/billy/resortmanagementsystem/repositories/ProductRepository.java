package org.billy.resortmanagementsystem.repositories;

import org.billy.resortmanagementsystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from  Product p where p.name =:keyword")
    List<Product> searchProducts(String keyword);


}
