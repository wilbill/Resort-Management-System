package org.billy.resortmanagementsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.billy.resortmanagementsystem.domain.ProductType;


@Data
public class CreateProductRequest {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    private double price;

    private String description;

    private String excerpt;

    private ProductType type;
}
