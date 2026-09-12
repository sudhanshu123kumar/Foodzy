package com.EcommerceWeb.Foodzy.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(length = 200)
    private  String description;

    @Column(nullable = false)
    private Double price;

//    @Column(unique = true)
//    private String sku;

    private Double discountPrice;

    private String brand;

    private Double rating;

    private Integer stockQuantity;

    private Boolean isAvailable = true;

    @ElementCollection
    private List<String> imageUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
