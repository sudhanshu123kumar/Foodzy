package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoFullResponse {

    private Long categoryId;

    private String categoryName;

    private String description;

    private String imageUrl;

    private List<ProductSimpleResponseDto> products;

}
