package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.CategoryDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoFullResponse;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoSimpleResponse;
import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.ServicesInterface.CategoryService;
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
@RequestMapping("/api/categories")

@Tag(
        name = "Category Module",
        description = "APIs for Category Management"
)
public class CategoryController {

   @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Create Category",
            description = "Creates a new category along with its image."
    )
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDtoSimpleResponse> createCategory(@Valid @RequestPart("category") CategoryDtoRequest categoryDtoRequest
            ,@RequestPart("image") MultipartFile image){

        CategoryDtoSimpleResponse response = this.categoryService.createCategory(categoryDtoRequest, image);
        return new ResponseEntity<CategoryDtoSimpleResponse>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Category",
            description = "Updates an existing category and its image."
    )
    @PutMapping(value = "/{categoryId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDtoSimpleResponse> updateCategory(@Valid @RequestPart("category") CategoryDtoRequest categoryDtoRequest
    ,@RequestPart("image") MultipartFile image, @PathVariable Long categoryId){

        CategoryDtoSimpleResponse updateCategory = this.categoryService.updateCategory(categoryDtoRequest, image,categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Category By ID",
            description = "Returns complete category details using category ID."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDtoFullResponse> categoryGetById(@PathVariable Long categoryId){

        CategoryDtoFullResponse category = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<CategoryDtoFullResponse>(category, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Categories",
            description = "Returns paginated list of all categories with sorting support."
    )
      @GetMapping("/")
      public ResponseEntity<PageResponse<CategoryDtoSimpleResponse>> AllcategoryGet(
              @RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
              Integer pageNumber,
              @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
              Integer pageSize,
              @RequestParam(value = "sortBy", defaultValue = AppConstants.CATEGORY_SORT_BY, required = false)
              String sortBy,
              @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
              String sortDir
      ){

      PageResponse<CategoryDtoSimpleResponse> categories = this.categoryService.getAllCategories(
              pageNumber,
              pageSize,
              sortBy,
              sortDir
      );
      return new ResponseEntity<PageResponse<CategoryDtoSimpleResponse>>(categories, HttpStatus.OK);
     }

    @Operation(
            summary = "Delete Category",
            description = "Deletes a category using its ID."
    )
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId){

         this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse<Void>>(new ApiResponse("category deleted successFully", ResponseStatus.SUCCESS, null), HttpStatus.OK);
    }

}
