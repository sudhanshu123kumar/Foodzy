package com.EcommerceWeb.Foodzy.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 40, message = "Category name must be between 3 and 40 characters")
    private String categoryName;

    @Size(max = 200, message = "Description can not exceed 200 characters")
    private String description;

    private String imageUrl;
}
