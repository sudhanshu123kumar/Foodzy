package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.PaymentRequestDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentResponseDto;
import com.EcommerceWeb.Foodzy.ServicesInterface.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")

@Tag(
        name = "Payment Module",
        description = "APIs for Payment Management"
)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(
            summary = "Create Payment",
            description = "Creates a payment for the specified order."
    )
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestBody PaymentRequestDto requestDto
    ) {

        PaymentResponseDto responseDto =
                this.paymentService.createPayment(requestDto);

        return new ResponseEntity<PaymentResponseDto>(responseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Payment By Order ID",
            description = "Returns payment details for the given order ID."
    )
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(
            @PathVariable Long orderId
    ) {


        PaymentResponseDto responseDto =
                this.paymentService.getPaymentByOrderId(orderId);


        return new ResponseEntity<PaymentResponseDto>(responseDto, HttpStatus.OK);
    }


}
