package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponseDto {

    private Long totalUsers;

    private Long totalProducts;

    private Long totalOrders;

    private Double totalRevenue;

    private List<OrderResponseDto> recentOrders;

    private List<ProductSimpleResponseDto> topSellingProducts;
}
