package com.EcommerceWeb.Foodzy.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long orderItemId;

    private Long productId;
    private String productName;
    private String imageUrl;

    private Integer quantity;
    private Double price;
    private Double subTotal;

}
