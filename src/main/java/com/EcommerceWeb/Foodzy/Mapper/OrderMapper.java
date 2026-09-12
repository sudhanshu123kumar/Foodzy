package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMaper orderItemMaper;

    public OrderResponseDto toResponse(Order order){

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())

                .userId(order.getUser().getUserId())
                .userName(order.getUser().getName())

                .totalAmount(order.getTotalPrice())
                .totalItems(order.getTotalItems())
                .orderStatus(order.getOrderStatus())

                .paymentMethod(order.getPaymentMethod())
                .shippingAddress(order.getShippingAddress())

                .orderDate(order.getOrderDate())

                .orderItems(
                        order.getOrderItems()
                                .stream()
                                .map((item) -> orderItemMaper.toResponse(item))
                                .collect(Collectors.toList())
                )
                .build();
    }

}
