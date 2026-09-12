package com.EcommerceWeb.Foodzy.Repository;

import com.EcommerceWeb.Foodzy.Entities.Order;
import com.EcommerceWeb.Foodzy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findTop5ByOrderByOrderDateDesc();
}
