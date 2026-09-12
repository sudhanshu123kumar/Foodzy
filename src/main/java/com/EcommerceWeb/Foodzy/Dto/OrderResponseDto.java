package com.EcommerceWeb.Foodzy.Dto;

import com.EcommerceWeb.Foodzy.Enum.OrderStatus;
import com.EcommerceWeb.Foodzy.Enum.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;

    private Long userId;
    private String userName;

    private Double totalAmount;
    private Integer totalItems;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private String shippingAddress;

    private LocalDateTime orderDate;

    private List<OrderItemResponseDto> orderItems;
}
