package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.AddToCartDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CartItemResponseDto;
import com.EcommerceWeb.Foodzy.Dto.CartResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Cart;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemMapper cartItemMapper;




    public CartResponseDto toResponse(Cart cart) {

        CartResponseDto responseDto =
                modelMapper.map(
                        cart,
                        CartResponseDto.class
                );


        responseDto.setUserId(
                cart.getUser().getUserId()
        );


        responseDto.setCartItems(

                cart.getCartItems()

                        .stream()

                        .map((cartItem) -> {

                            CartItemResponseDto itemResponse =
                                    this.cartItemMapper.toResponse(cartItem);

                            return itemResponse;

                        })

                        .collect(Collectors.toList())

        );


        return responseDto;

    }
}
