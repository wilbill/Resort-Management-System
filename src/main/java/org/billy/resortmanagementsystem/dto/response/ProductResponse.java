package org.billy.resortmanagementsystem.dto.response;

import lombok.Data;
import org.billy.resortmanagementsystem.domain.Product;
import org.billy.resortmanagementsystem.domain.ProductType;
import org.billy.resortmanagementsystem.exception.ProductNotFoundException;

@Data
public class ProductResponse {

    private Integer id;

    private String name;

    private double price;

    private String description;

    private String excerpt;

    private ProductType type;

    public static ProductResponse from(Product product) {
        if (product == null) {
            throw new ProductNotFoundException("Product cannot be null");
        }

        ProductResponse response = new ProductResponse();
        response.id = product.getId();
        response.description = product.getDescription();
        response.price = product.getPrice();
        response.excerpt = product.getExcerpt();
        response.name = product.getName();
        response.type = product.getType();
        return response;
    }
}
