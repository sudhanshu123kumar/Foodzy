package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.ProductFullResponseDto;
import com.EcommerceWeb.Foodzy.Dto.ProductRequestDto;
import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Product toEntity(ProductRequestDto requestDto){

        return this.modelMapper.map(
                requestDto, Product.class
        );
    }

    public ProductSimpleResponseDto toSimpleResponse(Product product){

        ProductSimpleResponseDto dto =
                this.modelMapper.map(
                        product,
                        ProductSimpleResponseDto.class
                );

        if (product.getImageUrls() != null &&
                !product.getImageUrls().isEmpty()) {

            dto.setImageUrl(
                    product.getImageUrls().get(0)
            );
        }

        return dto;
    }

    public ProductSimpleResponseDto toSimpleResponseWithCategory(Product product) {

        ProductSimpleResponseDto dto =
                modelMapper.map(
                        product,
                        ProductSimpleResponseDto.class
                );

        dto.setCategoryName(
                product.getCategory().getCategoryName()
        );

        return dto;
    }

    public ProductFullResponseDto toFullResponse(Product product){

        return this.modelMapper.map(
                product, ProductFullResponseDto.class
        );
    }
}
