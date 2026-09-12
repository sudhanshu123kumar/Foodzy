package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.AdminDashboardResponseDto;
import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Order;
import com.EcommerceWeb.Foodzy.Entities.Product;
import com.EcommerceWeb.Foodzy.Mapper.OrderMapper;
import com.EcommerceWeb.Foodzy.Mapper.ProductMapper;
import com.EcommerceWeb.Foodzy.Repository.*;
import com.EcommerceWeb.Foodzy.ServicesInterface.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private  UserRepositories userRepositories;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    OrderItemRepository orderItemRepository;



    @Override
    public AdminDashboardResponseDto getDashboardData() {

        AdminDashboardResponseDto response = new AdminDashboardResponseDto();

        response.setTotalUsers(getTotalUsers());
        response.setTotalProducts(getTotalProducts());
        response.setTotalOrders(getTotalOrders());
        response.setTotalRevenue(getTotalRevenue());
        response.setRecentOrders(getRecentOrders());
        response.setTopSellingProducts(getTopSellingProducts());

        return response;
    }


    @Override
    public Long getTotalUsers() {
        return this.userRepositories.count();
    }


    @Override
    public Long getTotalProducts() {
        return this.productRepository.count();
    }


    @Override
    public Long getTotalOrders() {
        return this.orderRepository.count();
    }


    @Override
    public Double getTotalRevenue() {

        return this.paymentRepository.findAll()
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();
    }


    @Override
    public List<OrderResponseDto> getRecentOrders() {

        List<Order> orders = this.orderRepository
                .findTop5ByOrderByOrderDateDesc();

        return orders.stream()
                .map(order -> orderMapper.toResponse(order))
                .toList();

    }


    @Override
    public List<ProductSimpleResponseDto> getTopSellingProducts() {

        List<Product> products = this.orderItemRepository.findTopSellingProducts(PageRequest.of(0,5));

        return products.stream()
                .map(product -> productMapper.toSimpleResponse(product))
                .toList();

    }
}

