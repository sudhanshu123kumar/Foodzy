package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleResponseDto {

    private Long productId;

    private String productName;

    private Double price;

    private Double discountPrice;

    private Double rating;

    private Integer stockQuantity;

    private String imageUrl;

    private String categoryName;
}
