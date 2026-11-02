package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.PaymentRequestDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentResponseDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentVerificationDto;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto requestDto);

    PaymentResponseDto getPaymentByOrderId(Long orderId);

    PaymentResponseDto verifyPayment(
            PaymentVerificationDto request);

}
