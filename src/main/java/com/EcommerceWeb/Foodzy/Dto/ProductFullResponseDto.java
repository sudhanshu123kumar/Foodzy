package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFullResponseDto {

    private Long productId;

    private String productName;

    private String description;

    private Double price;

    private Double discountPrice;

    private Integer stockQuantity;

    private String brand;

    private Double rating;

    private List<String> imageUrls;

    private Boolean isAvailable;

    private CategoryDtoSimpleResponse category;
}
