package com.EcommerceWeb.Foodzy.Dto;

import com.EcommerceWeb.Foodzy.Enum.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderRequestDto {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
