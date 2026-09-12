package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.AdminDashboardResponseDto;
import com.EcommerceWeb.Foodzy.Dto.OrderResponseDto;
import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;
import com.EcommerceWeb.Foodzy.ServicesInterface.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

@Tag(
        name = "Admin Module",
        description = "APIs for Admin Dashboard and Management"
)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(
            summary = "Get Dashboard Data",
            description = "Returns complete dashboard information including users, products, orders, revenue, recent orders and top selling products."
    )
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponseDto> getDashboardData() {

        AdminDashboardResponseDto response =
                adminService.getDashboardData();

        return new ResponseEntity<AdminDashboardResponseDto>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Total Users",
            description = "Returns the total number of registered users."
    )
    @GetMapping("/total-users")
    public ResponseEntity<Long> getTotalUsers() {

        Long response = adminService.getTotalUsers();

        return new ResponseEntity<Long>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Total Products",
            description = "Returns the total number of available products."
    )
    @GetMapping("/total-products")
    public ResponseEntity<Long> getTotalProducts() {

        Long response = adminService.getTotalProducts();

        return new ResponseEntity<Long>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Total Orders",
            description = "Returns the total number of placed orders."
    )
    @GetMapping("/total-orders")
    public ResponseEntity<Long> getTotalOrders() {

        Long response = adminService.getTotalOrders();

        return new ResponseEntity<Long>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Total Revenue",
            description = "Returns the total revenue generated from successful payments."
    )
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {

        Double response = adminService.getTotalRevenue();

        return new ResponseEntity<Double>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Recent Orders",
            description = "Returns the latest five orders placed in the system."
    )
    @GetMapping("/recent-orders")
    public ResponseEntity<List<OrderResponseDto>> getRecentOrders() {

        List<OrderResponseDto> response =
                adminService.getRecentOrders();

        return new ResponseEntity<List<OrderResponseDto>>(
                response,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Get Top Selling Products",
            description = "Returns the top five best-selling products."
    )
    @GetMapping("/top-selling-products")
    public ResponseEntity<List<ProductSimpleResponseDto>> getTopSellingProducts() {

        List<ProductSimpleResponseDto> response =
                adminService.getTopSellingProducts();

        return new ResponseEntity<List<ProductSimpleResponseDto>>(
                response,
                HttpStatus.OK
        );
    }
}

