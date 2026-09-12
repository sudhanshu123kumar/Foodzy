package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.CategoryDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoFullResponse;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoSimpleResponse;
import com.EcommerceWeb.Foodzy.Entities.Category;
import com.EcommerceWeb.Foodzy.Entities.Product;
import com.EcommerceWeb.Foodzy.Exceptions.DuplicateResourceException;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.CategoryMapper;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.Repository.CategoryRepository;
import com.EcommerceWeb.Foodzy.ServicesInterface.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

   @Autowired
   private CategoryRepository categoryRepository;
   @Autowired
   private CategoryMapper categoryMapper;
   @Autowired
   private ImageFileServiceImpl imageFileService;

    @Value("${project.image}")
    private String path;

    @Override
    public CategoryDtoSimpleResponse createCategory(CategoryDtoRequest categoryDtoRequest, MultipartFile image) {

        if (this.categoryRepository.existsByCategoryName(categoryDtoRequest.getCategoryName())){

            throw new DuplicateResourceException(
                    "Category is Already Exists"
            );
        }
        Category category = this.categoryMapper.toEntity(categoryDtoRequest);

        try {
            String imageName = this.imageFileService.uploadImage(path, image);
             category.setImageUrl(imageName);
        }catch (IOException e){
            throw new RuntimeException("Image upload failed", e);
        }
        Category savedCategory = this.categoryRepository.save(category);
        return this.categoryMapper.toSimpleResponse(savedCategory);
    }

    @Override
    public CategoryDtoSimpleResponse updateCategory(CategoryDtoRequest categoryDtoRequest, MultipartFile image, Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        category.setCategoryName(categoryDtoRequest.getCategoryName());
        category.setDescription(categoryDtoRequest.getDescription());

        if (image != null && !image.isEmpty()){

            this.imageFileService.deleteImage(path, category.getImageUrl());

            try {
                String imageName = this.imageFileService.uploadImage(path, image);
                category.setImageUrl(imageName);
            }catch (IOException e){
                throw new RuntimeException("Image upload failed", e);
            }
        }
        Category updateCategory = this.categoryRepository.save(category);

        return this.categoryMapper.toSimpleResponse(updateCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        this.imageFileService.deleteImage(path,category.getImageUrl());
        this.categoryRepository.delete(category);
    }

    @Override
    public PageResponse<CategoryDtoSimpleResponse> getAllCategories(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );

        Page<Category> categories = this.categoryRepository.findAll(pageable);


       List<CategoryDtoSimpleResponse> categoryDtoSimpleResponses = categories.getContent()
               .stream()
               .map((category) -> {
                   CategoryDtoSimpleResponse response = this.categoryMapper.toSimpleResponse(category);

                   return response;
               }).collect(Collectors.toList());

       return PageResponse.<CategoryDtoSimpleResponse>builder()
               .productData(categoryDtoSimpleResponses)
               .pageNumber(categories.getNumber())
               .pageSize(categories.getSize())
               .totalElements(categories.getTotalElements())
               .totalPages(categories.getTotalPages())
               .lastPage(categories.isLast())
               .build();

    }

    @Override
    public CategoryDtoFullResponse getCategoryById(Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        return this.categoryMapper.toFullResponse(category);
    }

}
