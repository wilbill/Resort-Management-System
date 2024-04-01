package org.billy.resortmanagementsystem.services.impl;//package miu.edu.resort.services.impl;

import jakarta.persistence.EntityNotFoundException;
//import miu.edu.resort.domain.Product;
//import miu.edu.resort.dto.request.CreateProductRequest;
//import miu.edu.resort.dto.response.ProductResponse;
//import miu.edu.resort.exception.ProductNotFoundException;
//import miu.edu.resort.repositories.ProductRepository;
import org.billy.resortmanagementsystem.domain.Product;
import org.billy.resortmanagementsystem.dto.request.CreateProductRequest;
import org.billy.resortmanagementsystem.dto.response.ProductResponse;
import org.billy.resortmanagementsystem.exception.ProductNotFoundException;
import org.billy.resortmanagementsystem.repositories.ProductRepository;
import org.billy.resortmanagementsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse create(CreateProductRequest request) {
        // TODO Check if Product exist already...later
        Product product = new Product();
        product.setType(request.getType());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setExcerpt(request.getExcerpt());
        product.setPrice(request.getPrice());
        product = productRepository.save(product);
        return ProductResponse.from(product);

    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(String.format("Product with id %s not found", productId)));
        return ProductResponse.from(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.searchProducts(keyword);
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(Integer productId, CreateProductRequest request) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // Update the product properties based on the request
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setExcerpt(request.getExcerpt());
            product.setType(request.getType());
            product.setPrice(request.getPrice());

            productRepository.save(product);

            return ProductResponse.from(product);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }

    @Override
    public void deleteById(Integer productId) {
        productRepository.deleteById(productId);
    }

}
