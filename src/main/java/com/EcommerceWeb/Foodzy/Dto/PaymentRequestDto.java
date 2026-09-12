package com.EcommerceWeb.Foodzy.Dto;

import com.EcommerceWeb.Foodzy.Enum.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "Order Id is required")
    private Long orderId;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;
}
