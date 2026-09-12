package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.AddToCartDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CartResponseDto;

public interface CartService {

    CartResponseDto addToCart(AddToCartDtoRequest requestDto);

    CartResponseDto getMyCart();

    CartResponseDto updateCartItemQuantity(Long cartItemId, Integer quantity);

    CartResponseDto removeCartItem(Long cartItemId);

    void clearMyCart();
}
