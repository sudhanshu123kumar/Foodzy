package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.*;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    // create product
    ProductSimpleResponseDto createProduct(ProductRequestDto productRequestDto,  List<MultipartFile> images);

    // update product
    ProductSimpleResponseDto updateProduct(ProductRequestDto productRequestDto, List<MultipartFile> images, Long productId);

    // delete product
    void deleteProduct(Long productId);

    // get all products
    PageResponse<ProductSimpleResponseDto> getAllProducts(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir);

//    searching/ filter

    PageResponse<ProductSimpleResponseDto> searchProducts(

            String keyword,
            String brand,
            String category,
            Double minPrice,
            Double maxPrice,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    );

    // get product by id

    ProductFullResponseDto getProductById(Long productId);
}
