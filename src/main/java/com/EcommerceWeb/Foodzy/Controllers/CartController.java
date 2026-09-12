package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.AddToCartDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CartResponseDto;
import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.EcommerceWeb.Foodzy.ServicesInterface.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")

@Tag(
        name = "Cart Module",
        description = "APIs for Shopping Cart Management"
)
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(
            summary = "Add Product to Cart",
            description = "Adds a product to the user's cart. If the product already exists in the cart, its quantity is increased."
    )
    @PostMapping("/")
    public ResponseEntity<CartResponseDto> addToCart(
            @Valid @RequestBody AddToCartDtoRequest requestDto
            ){

        CartResponseDto cartResponseDto = this.cartService.addToCart(requestDto);

        return new ResponseEntity<CartResponseDto>(cartResponseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get My Cart",
            description = "Returns the current user's cart with all cart items."
    )
    @GetMapping()
    public ResponseEntity<CartResponseDto> getMyCart(){

        CartResponseDto cartResponseDto = this.cartService.getMyCart();
        return new ResponseEntity<CartResponseDto>(cartResponseDto, HttpStatus.OK);

    }

    @Operation(
            summary = "Update Cart Item Quantity",
            description = "Updates the quantity of a specific cart item."
    )
    @PutMapping("/item/{cartItemId}/quantity")
    public ResponseEntity<CartResponseDto> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {

        CartResponseDto responseDto =
                this.cartService.updateCartItemQuantity(cartItemId, quantity);

        return new ResponseEntity<CartResponseDto>(responseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Remove Cart Item",
            description = "Removes a specific product from the cart."
    )
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<CartResponseDto> removeCartItem(
            @PathVariable Long cartItemId) {

        CartResponseDto responseDto =
                this.cartService.removeCartItem(cartItemId);

        return new ResponseEntity<CartResponseDto>(responseDto, HttpStatus.OK);

    }

    @Operation(
            summary = "Clear Shopping Cart",
            description = "Removes all items from the current user's cart."
    )
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {

        this.cartService.clearMyCart();

        return new ResponseEntity<ApiResponse<Void>>(new ApiResponse("cart clear successFully", ResponseStatus.SUCCESS,null), HttpStatus.OK);

    }

}
