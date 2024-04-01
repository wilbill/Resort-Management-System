package org.billy.resortmanagementsystem.services;

import org.billy.resortmanagementsystem.dto.request.CreateProductRequest;
import org.billy.resortmanagementsystem.dto.response.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);

    List<ProductResponse> findAll();

    ProductResponse findById(Integer productId);

    List<ProductResponse> searchProducts(String keyword);

    ProductResponse update(Integer productId, CreateProductRequest request);

    void deleteById(Integer productId);


}
