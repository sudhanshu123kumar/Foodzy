package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.CartItemResponseDto;
import com.EcommerceWeb.Foodzy.Entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

     @Autowired
     private ModelMapper modelMapper;


    public CartItemResponseDto toResponse(CartItem cartItem) {

        CartItemResponseDto responseDto =
                modelMapper.map(
                        cartItem,
                        CartItemResponseDto.class
                );


        responseDto.setProductId(
                cartItem.getProduct().getProductId()
        );

        responseDto.setProductName(
                cartItem.getProduct().getProductName()
        );

        responseDto.setProductImage(
                cartItem.getProduct().getImageUrls().get(0)
        );

        responseDto.setTotalPrice(

                cartItem.getPrice()

                        *

                        cartItem.getQuantity()

        );


        return responseDto;

    }
}
