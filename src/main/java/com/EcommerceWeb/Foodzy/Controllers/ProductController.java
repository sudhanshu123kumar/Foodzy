package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.*;
import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.ServicesImpl.CategoryServiceImpl;
import com.EcommerceWeb.Foodzy.ServicesImpl.ProductServiceImpl;
import com.EcommerceWeb.Foodzy.Utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")

@Tag(
        name = "Product Module",
        description = "APIs for Product Management"
)
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Operation(
            summary = "Create Product",
            description = "Creates a new product with image."
    )
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductSimpleResponseDto>> createProduct(@Valid @RequestPart ("product")ProductRequestDto productRequestDto
    , @RequestPart("images") List<MultipartFile> images)
            {

        ProductSimpleResponseDto response = this.productServiceImpl.createProduct(productRequestDto, images);
                ApiResponse<ProductSimpleResponseDto> apiResponse =
                        new ApiResponse<>(
                                "product created successfully",
                                ResponseStatus.CREATE,
                                response
                        );
        return new ResponseEntity<ApiResponse<ProductSimpleResponseDto>>(apiResponse,HttpStatus.CREATED);

    }

    @Operation(
            summary = "Update Product",
            description = "Updates product details and image."
    )
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductSimpleResponseDto>> updateProduct(@Valid @RequestPart ("product")ProductRequestDto productRequestDto
            , @RequestPart ("image")List<MultipartFile> images, @PathVariable Long productId) {

        ProductSimpleResponseDto updateProduct = this.productServiceImpl.updateProduct(productRequestDto, images, productId);
        ApiResponse<ProductSimpleResponseDto> updatedResponse =
                new ApiResponse<>(
                        "Product updated successfully",
                        ResponseStatus.SUCCESS,
                        updateProduct
                );
        return new ResponseEntity<ApiResponse<ProductSimpleResponseDto>>(updatedResponse,HttpStatus.OK);

    }

    @Operation(
            summary = "Get All Products",
            description = "Returns paginated list of all products."
    )
    @GetMapping("/")
    public ResponseEntity<ApiResponse<PageResponse<ProductSimpleResponseDto>>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
            Integer pageNumber,

            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
            Integer pageSize,

            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false)
            String sortBy,

            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
            String sortDir
    ){

        PageResponse<ProductSimpleResponseDto> response = this.productServiceImpl.getAllProducts(
                pageNumber,
                pageSize,
                sortBy,
                sortDir
        );

        ApiResponse<PageResponse<ProductSimpleResponseDto>> ApiResponse =
                new ApiResponse<>(
                        "products fetch successfully",
                        ResponseStatus.SUCCESS,
                        response
                );
        return new ResponseEntity<ApiResponse<PageResponse<ProductSimpleResponseDto>>>(ApiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Product",
            description = "Deletes a product using its ID."
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Long productId){

        this.productServiceImpl.deleteProduct(productId);
        return new ResponseEntity<ApiResponse<Void>>(
                new ApiResponse(
                        "product delete successfully",
                        ResponseStatus.SUCCESS,
                        null), HttpStatus.OK);
    }

    @Operation(
            summary = "Search Products",
            description = "Search products using keyword, category, brand and price filters."
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ProductSimpleResponseDto>>> searchProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
            Integer pageNumber,

            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
            Integer pageSize,

            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false)
            String sortBy,

            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
            String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice


    ){

        PageResponse<ProductSimpleResponseDto> response = this.productServiceImpl.searchProducts(

                keyword,
                brand,
                category,
                minPrice,
                maxPrice,
                pageNumber,
                pageSize,
                sortBy,
                sortDir
        );

        ApiResponse<PageResponse<ProductSimpleResponseDto>> apiResponse =
                new ApiResponse<>(
                        response.getProductData().isEmpty()
                        ?"No products found"
                         :"search products fetch successfully",
                        ResponseStatus.SUCCESS,
                        response
                );
        return new ResponseEntity<ApiResponse<PageResponse<ProductSimpleResponseDto>>>(apiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Product By ID",
            description = "Returns complete details of a product."
    )
    @GetMapping("/{productId:\\d+}")
    public ResponseEntity<ApiResponse<ProductFullResponseDto>> getProductById(@PathVariable Long productId){

        ProductFullResponseDto product = this.productServiceImpl.getProductById(productId);

        ApiResponse<ProductFullResponseDto> apiResponse =
                new ApiResponse<>(
                        "products fetch successfully",
                        ResponseStatus.SUCCESS,
                        product
                );

        return new ResponseEntity<ApiResponse<ProductFullResponseDto>>(apiResponse, HttpStatus.OK);
    }
}
