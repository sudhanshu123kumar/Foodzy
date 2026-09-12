package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.OrderItemResponseDto;
import com.EcommerceWeb.Foodzy.Entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMaper {

    public OrderItemResponseDto toResponse(OrderItem orderItem){

        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getProductId())
                .productName(orderItem.getProduct().getProductName())
                .imageUrl(orderItem.getProduct().getImageUrls().get(0))

                .quantity(orderItem.getProductQuantity())
                .price(orderItem.getProductPrice())
                .subTotal(orderItem.getTotalProductPrice())
                .build();

    }
}
