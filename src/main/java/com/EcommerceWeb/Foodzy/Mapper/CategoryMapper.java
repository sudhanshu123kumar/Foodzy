package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.CategoryDtoFullResponse;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CategoryDtoSimpleResponse;
import com.EcommerceWeb.Foodzy.Entities.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Category toEntity(CategoryDtoRequest requestDto){

       return this.modelMapper.map(
                requestDto, Category.class
        );
    }

    public CategoryDtoSimpleResponse toSimpleResponse(Category category){

        return this.modelMapper.map(
                category, CategoryDtoSimpleResponse.class
        );
    }

    public CategoryDtoFullResponse toFullResponse(Category category){

        return this.modelMapper.map(
                category, CategoryDtoFullResponse.class
        );
    }

}
