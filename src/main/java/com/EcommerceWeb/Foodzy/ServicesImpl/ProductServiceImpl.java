package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.ProductFullResponseDto;
import com.EcommerceWeb.Foodzy.Dto.ProductRequestDto;
import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Category;
import com.EcommerceWeb.Foodzy.Entities.Product;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.ProductMapper;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.Repository.CategoryRepository;
import com.EcommerceWeb.Foodzy.Repository.ProductRepository;
import com.EcommerceWeb.Foodzy.ServicesInterface.ProductService;
import com.EcommerceWeb.Foodzy.Specification.ProductSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ImageFileServiceImpl imageFileService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductSimpleResponseDto createProduct(ProductRequestDto productRequestDto,  List<MultipartFile> images) {

        Product product = this.productMapper.toEntity(productRequestDto);

        product.setProductId(null);

        System.out.println("After mapping ID: " + product.getProductId());

        Category category = this.categoryRepository.findById(product.getCategory().getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", productRequestDto.getCategoryId()));

        product.setCategory(category);

        try {
            List<String> imageUrls = new ArrayList<>();

            for (MultipartFile file : images) {

                String imageName = this.imageFileService.uploadImage(path, file);

                imageUrls.add(imageName);
            }

            product.setImageUrls(imageUrls);
        }catch (IOException e){
            throw new RuntimeException("Image upload failed", e);
        }

        product.setIsAvailable(true);

        System.out.println("Product id before save: " + product.getProductId());

        Product saveProduct = this.productRepository.save(product);

        ProductSimpleResponseDto response =
                this.productMapper.toSimpleResponse(saveProduct);

        response.setCategoryName(
                product.getCategory().getCategoryName()
        );
        return response;

    }

    @Override
    public ProductSimpleResponseDto updateProduct(ProductRequestDto productRequestDto, List<MultipartFile> images, Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        product.setProductName(productRequestDto.getProductName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setBrand(productRequestDto.getBrand());
        product.setRating(productRequestDto.getRating());
        product.setDiscountPrice(productRequestDto.getDiscountPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());

        if (images != null && !images.isEmpty()){

            for (String oldImage : product.getImageUrls()) {
                this.imageFileService.deleteImage(path, oldImage);
            }

            List<String> imageUrls = new ArrayList<>();

            try {
                for (MultipartFile file : images) {

                    String imageName =
                            this.imageFileService.uploadImage(path, file);

                    imageUrls.add(imageName);
                }

                product.setImageUrls(imageUrls);
            }catch (IOException e){
                throw new RuntimeException("Image upload failed", e);
            }
        }

        Product updateProduct = this.productRepository.save(product);

        ProductSimpleResponseDto response =
                this.productMapper.toSimpleResponse(updateProduct);

        response.setCategoryName(
                product.getCategory().getCategoryName()
        );
        return response;
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        for (String imageUrl : product.getImageUrls()) {
            this.imageFileService.deleteImage(path, imageUrl);
        }
        this.productRepository.delete(product);

    }

    @Override
    public ProductFullResponseDto getProductById(Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        return this.productMapper.toFullResponse(product);
    }

    @Override
    public PageResponse<ProductSimpleResponseDto> getAllProducts(
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

        Page<Product> productPage = this.productRepository.findAll(pageable);

        List<ProductSimpleResponseDto> productSimpleResponseDtos = productPage.getContent()
                .stream()
                .map((product) -> {
                    ProductSimpleResponseDto responseDto = this.productMapper.toSimpleResponse(product);

                    return responseDto;
                }).collect(Collectors.toList());

        return PageResponse.<ProductSimpleResponseDto>builder()
                .productData(productSimpleResponseDtos)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public PageResponse<ProductSimpleResponseDto> searchProducts(
            String keyword,
            String brand,
            String category,
            Double minPrice,
            Double maxPrice,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );

        Specification<Product> specification =

                Specification
                        .where(
                                ProductSpecification.hasKeyword(keyword)
                        )
                        .and(
                                ProductSpecification.hasBrand(brand)
                        )
                        .and(
                                ProductSpecification.hasCategory(category)
                        )
                        .and(
                                ProductSpecification.hasPriceBetween(
                                        minPrice,
                                        maxPrice
                                )
                        );

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductSimpleResponseDto> productSimpleResponseDtos = productPage.getContent()
                .stream()
                .map((product) -> {
                    ProductSimpleResponseDto responseDto = this.productMapper.toSimpleResponse(product);

                    return responseDto;
                }).collect(Collectors.toList());


        return PageResponse.<ProductSimpleResponseDto>builder()
                .productData(productSimpleResponseDtos)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }
}
