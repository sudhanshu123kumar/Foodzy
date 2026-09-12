package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.PlaceOrderRequestDto;
import com.EcommerceWeb.Foodzy.Enum.OrderStatus;
import com.EcommerceWeb.Foodzy.ServicesInterface.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")

@Tag(
        name = "Order Module",
        description = "APIs for Order Management"
)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Place Order",
            description = "Places a new order using the current cart items."
    )
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @RequestBody PlaceOrderRequestDto requestDto
            ){

        OrderResponseDto response = this.orderService.placeOrder(requestDto);

        return new ResponseEntity<OrderResponseDto>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get My Orders",
            description = "Returns all orders placed by the current user."
    )
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderByUser(){

        List<OrderResponseDto> response = this.orderService.getMyOrders();

        return new ResponseEntity<List<OrderResponseDto>>(response, HttpStatus.OK);

    }

    @Operation(
            summary = "Get Order By ID",
            description = "Returns complete order details using order ID."
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId){

        OrderResponseDto response = this.orderService.getOrderById(orderId);

        return new ResponseEntity<OrderResponseDto>(response, HttpStatus.OK);

    }

    @Operation(
            summary = "Cancel Order",
            description = "Cancels an existing order."
    )
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable Long orderId) {

        OrderResponseDto response = orderService.cancelOrder(orderId);

        return new ResponseEntity<OrderResponseDto>(response, HttpStatus.OK);


    }

    @Operation(
            summary = "Update Order Status",
            description = "Updates the status of an order (PENDING, SHIPPED, DELIVERED, CANCELLED, etc.)."
    )
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(

            @PathVariable Long orderId,

            @RequestParam OrderStatus orderStatus) {

        OrderResponseDto response =
                orderService.updateOrderStatus(
                        orderId,
                        orderStatus);

        return new ResponseEntity<OrderResponseDto>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Orders",
            description = "Returns the list of all orders in the system."
    )
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){

        List<OrderResponseDto> orders = orderService.getAllOrders();

        return new ResponseEntity<List<OrderResponseDto>>(orders, HttpStatus.OK);
    }
}
