package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.PlaceOrderRequestDto;
import com.EcommerceWeb.Foodzy.Entities.*;
import com.EcommerceWeb.Foodzy.Enum.OrderStatus;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.OrderItemMaper;
import com.EcommerceWeb.Foodzy.Mapper.OrderMapper;
import com.EcommerceWeb.Foodzy.Repository.*;
import com.EcommerceWeb.Foodzy.ServicesInterface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMaper orderItemMaper;


    @Override
    public OrderResponseDto placeOrder(PlaceOrderRequestDto requestDto) {

        User user = this.userRepositories.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", requestDto.getUserId()));

        Cart cart = this.cartRepository.findByUserUserId(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("cart", "userId", requestDto.getUserId()));

        if (cart.getCartItems().isEmpty()){
            throw new RuntimeException("cart is empty");
        }

        Order order = new Order();

        order.setUser(user);

        order.setTotalItems(cart.getTotalItems());
        order.setTotalPrice(cart.getTotalPrice());

        order.setShippingAddress(requestDto.getShippingAddress());
        order.setPaymentMethod(requestDto.getPaymentMethod());

        order.setOrderDate(LocalDateTime.now());

        order.setOrderStatus(OrderStatus.PENDING);

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {

                throw new RuntimeException(
                        product.getProductName() + " is out of stock."
                );
            }
        }

        List<OrderItem> orderItems = new ArrayList<>();

        double totalPrice = 0;
        int totalItems = 0;

        for(CartItem cartItem : cart.getCartItems()){

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(cartItem.getProduct());

            orderItem.setProductQuantity(cartItem.getQuantity());

            orderItem.setProductPrice(cartItem.getPrice());

            double subTotal = cartItem.getPrice() * cartItem.getQuantity();
            orderItem.setTotalProductPrice(subTotal);

            totalPrice += subTotal;
            totalItems += cartItem.getQuantity();

            Product product = cartItem.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity() - cartItem.getQuantity()
            );

            if (product.getStockQuantity() == 0) {

                product.setIsAvailable(false);
            }

            orderItems.add(orderItem);

        }
        order.setOrderItems(orderItems);

        System.out.println(order.getOrderStatus());

        Order savedOrder = this.orderRepository.save(order);

        cart.getCartItems().clear();

        cart.setTotalPrice(0.0);

        cart.setTotalItems(0);

        this.cartRepository.save(cart);

        return this.orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getMyOrders() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(order -> orderMapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order",
                                "orderId",
                                orderId));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Access Denied");
        }

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponseDto cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order",
                                "orderId",
                                orderId));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Access Denied");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toResponse(updatedOrder);

    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order",
                                "orderId",
                                orderId));

        if (order.getOrderStatus() == OrderStatus.CANCELLED){

            throw new RuntimeException(
                    "Cancelled order cannot be updated");
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {

            throw new RuntimeException(
                    "Delivered order cannot be updated");
        }

        order.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);

    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        List<Order> orders = this.orderRepository.findAll();

        return orders.stream()
                .map((order) -> {

                    OrderResponseDto responseDto = this.orderMapper.toResponse(order);

                    return responseDto;

                }).collect(Collectors.toList());
    }
}
