package com.EcommerceWeb.Foodzy.Specification;

import com.EcommerceWeb.Foodzy.Entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasKeyword(String keyword){

        return (root, query, criteriaBuilder) -> {

            if (keyword == null || keyword.isBlank()){

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(

                    criteriaBuilder.lower(root.get("productName")),

                    "%" + keyword.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> hasBrand(String brand){

        return (root, query, criteriaBuilder) -> {

            if (brand == null || brand.isBlank()){

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(

                    criteriaBuilder.lower(root.get("brand")),

                    "%" + brand.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> hasPriceBetween(Double minPrice,Double maxPrice){

        return (root, query, criteriaBuilder) -> {

            if (minPrice == null && maxPrice == null) {

                return criteriaBuilder.conjunction();
            }

            if(minPrice != null && maxPrice == null) {

                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );
            }

            if(minPrice == null && maxPrice != null) {

                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );
            }

            return criteriaBuilder.between(
                    root.get("price"),
                    minPrice,
                    maxPrice
            );
        };
    }

   public static Specification<Product> hasAvailable(Boolean available){

        return (root, query, criteriaBuilder) -> {
            if (available == null){

                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("isAvailable"),
                    available
            );
        };
   }

    public static Specification<Product> hasCategory(String categoryName){

        return (root, query, criteriaBuilder) -> {

            if (categoryName == null || categoryName.isBlank()){

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(

                    criteriaBuilder.lower(
                            root.get("category")
                                    .get("categoryName")),

                    "%" + categoryName.toLowerCase() + "%"
            );
        };
    }
}
