package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.AdminDashboardResponseDto;
import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;

import java.util.List;

public interface AdminService {

    AdminDashboardResponseDto getDashboardData();

    Long getTotalUsers();

    Long getTotalProducts();

    Long getTotalOrders();

    Double getTotalRevenue();

    List<OrderResponseDto> getRecentOrders();

    List<ProductSimpleResponseDto> getTopSellingProducts();
}
