package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoSimpleResponse {

    private Long categoryId;

    private String categoryName;

    private String description;

    private String imageUrl;
}
