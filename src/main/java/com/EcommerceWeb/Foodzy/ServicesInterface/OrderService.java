package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.PlaceOrderRequestDto;
import com.EcommerceWeb.Foodzy.Enum.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponseDto placeOrder(PlaceOrderRequestDto requestDto);

    List<OrderResponseDto> getMyOrders();

    OrderResponseDto getOrderById(Long orderId);

    OrderResponseDto cancelOrder(Long orderId);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus orderStatus);

    List<OrderResponseDto> getAllOrders();

}
