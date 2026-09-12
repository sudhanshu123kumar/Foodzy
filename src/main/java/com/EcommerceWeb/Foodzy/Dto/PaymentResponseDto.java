package com.EcommerceWeb.Foodzy.Dto;

import com.EcommerceWeb.Foodzy.Enum.PaymentMethod;
import com.EcommerceWeb.Foodzy.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {


    private Long paymentId;

    private Long orderId;

    private Double amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime paymentDate;
}
