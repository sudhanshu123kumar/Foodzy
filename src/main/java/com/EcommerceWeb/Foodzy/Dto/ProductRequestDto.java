package com.EcommerceWeb.Foodzy.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @PositiveOrZero(message = "Discount price cannot be negative")
    private Double discountPrice;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private String brand;

//    private String sku;
//    private Double rating;
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;

//    private List<MultipartFile> images;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
