package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.PaymentRequestDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDto toResponse(Payment payment){

        PaymentResponseDto responseDto = new PaymentResponseDto();

        responseDto.setPaymentId(payment.getPaymentId());
        responseDto.setOrderId(payment.getOrder().getOrderId());
        responseDto.setAmount(payment.getAmount());
        responseDto.setPaymentMethod(payment.getPaymentMethod());
        responseDto.setPaymentStatus(payment.getPaymentStatus());
        responseDto.setTransactionId(payment.getTransactionId());
        responseDto.setPaymentDate(payment.getPaymentDate());

        return responseDto;
    }
}
