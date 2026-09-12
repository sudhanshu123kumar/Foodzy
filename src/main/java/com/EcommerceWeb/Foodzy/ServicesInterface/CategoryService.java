package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.CategoryDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoFullResponse;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoSimpleResponse;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    // create category
    CategoryDtoSimpleResponse createCategory(CategoryDtoRequest categoryDtoRequest, MultipartFile image);

    // update category
    CategoryDtoSimpleResponse updateCategory(CategoryDtoRequest categoryDtoRequest, MultipartFile image, Long categoryId);

    // delete category
    void deleteCategory(Long categoryId);

    // get all categories (basic info)
    PageResponse<CategoryDtoSimpleResponse> getAllCategories(
            Integer pageNumber,
            Integer pageSize,
            String  sortBy,
            String sortDir
    );

    // get category by id with products
    CategoryDtoFullResponse getCategoryById(Long categoryId);
}
