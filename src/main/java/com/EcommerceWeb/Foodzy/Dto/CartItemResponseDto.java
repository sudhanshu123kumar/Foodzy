package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private String productImage;

    private Double price;

    private Integer quantity;

    private Double totalPrice;

}
