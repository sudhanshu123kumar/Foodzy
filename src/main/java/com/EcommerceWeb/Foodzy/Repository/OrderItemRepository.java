package com.EcommerceWeb.Foodzy.Repository;

import com.EcommerceWeb.Foodzy.Entities.OrderItem;
import com.EcommerceWeb.Foodzy.Entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderOrderId(Long orderId);

    @Query("""
        SELECT oi.product
        FROM OrderItem oi
        GROUP BY oi.product
        ORDER BY SUM(oi.productQuantity) DESC
        """)
    List<Product> findTopSellingProducts(Pageable pageable);

}
