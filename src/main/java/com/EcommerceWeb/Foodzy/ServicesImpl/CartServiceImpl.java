package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.AddToCartDtoRequest;
import com.EcommerceWeb.Foodzy.Dto.CartResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Cart;
import com.EcommerceWeb.Foodzy.Entities.CartItem;
import com.EcommerceWeb.Foodzy.Entities.Product;
import com.EcommerceWeb.Foodzy.Entities.User;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.CartMapper;
import com.EcommerceWeb.Foodzy.Repository.CartItemRepository;
import com.EcommerceWeb.Foodzy.Repository.CartRepository;
import com.EcommerceWeb.Foodzy.Repository.ProductRepository;
import com.EcommerceWeb.Foodzy.Repository.UserRepositories;
import com.EcommerceWeb.Foodzy.ServicesInterface.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepositories userRepositories;


    private void updateCartTotal(Cart cart){

        Double totalPrice = cart.getCartItems()
                .stream()
                .mapToDouble(item ->
                        item.getPrice() * item.getQuantity())
                .sum();


        Integer totalItem = cart.getCartItems()
                .stream()
                .mapToInt((cartItem) -> {
                    return cartItem.getQuantity();
                })
                .sum();

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);

        System.out.println("Total Price : " + totalPrice);
        System.out.println("Total Items : " + totalItem);
        this.cartRepository.save(cart);
    }

    @Override
    public CartResponseDto addToCart(AddToCartDtoRequest request) {

        User user = this.userRepositories.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", request.getUserId()));

        Product product = this.productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", request.getProductId()));

        Cart cart = this.cartRepository.findByUserUserId(request.getUserId())
                .orElseGet(() -> {

                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalItems(0);
                    newCart.setTotalPrice(0.0);

                    return cartRepository.save(newCart);

                });

        CartItem cartItem = this.cartItemRepository.findByCartCartIdAndProductProductId(
                cart.getCartId(),
                product.getProductId()
        ).orElse(null);

        if (cartItem != null){

            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

            cartItem.setPrice(product.getPrice());
        }else {

            cartItem  = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());

            cart.getCartItems().add(cartItem);

        }

        System.out.println("CartItem : " + cartItem);

        this.cartItemRepository.save(cartItem);


        updateCartTotal(cart);

        return this.cartMapper.toResponse(cart);
    }

    @Override
    public CartResponseDto getMyCart() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart",
                                "userId",
                                user.getUserId()
                        )
                );

        return this.cartMapper.toResponse(cart);
    }

    @Override
    public CartResponseDto updateCartItemQuantity(Long cartItemId, Integer quantity) {

        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "CartItem",
                                "id",
                                cartItemId
                        )
                );

        cartItem.setQuantity(quantity);

        this.cartItemRepository.save(cartItem);

        Cart cart = cartItem.getCart();

        updateCartTotal(cart);

        return this.cartMapper.toResponse(cart);
    }

    @Override
    public CartResponseDto removeCartItem(Long cartItemId) {

        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "CartItem",
                                "id",
                                cartItemId
                        )
                );

        Cart cart = cartItem.getCart();

        cart.getCartItems().remove(cartItem);

        this.cartItemRepository.delete(cartItem);

        updateCartTotal(cart);

        return this.cartMapper.toResponse(cart);

    }


    @Override
    @Transactional
    public void clearMyCart() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        Cart cart = this.cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart",
                                "userId",
                                user.getUserId()
                        )
                );

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }
}
