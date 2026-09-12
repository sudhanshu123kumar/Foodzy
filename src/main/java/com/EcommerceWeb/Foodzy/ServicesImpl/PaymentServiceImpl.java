package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.PaymentRequestDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentResponseDto;
import com.EcommerceWeb.Foodzy.Entities.Order;
import com.EcommerceWeb.Foodzy.Entities.Payment;
import com.EcommerceWeb.Foodzy.Entities.User;
import com.EcommerceWeb.Foodzy.Enum.PaymentStatus;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.PaymentMapper;
import com.EcommerceWeb.Foodzy.Repository.OrderRepository;
import com.EcommerceWeb.Foodzy.Repository.PaymentRepository;
import com.EcommerceWeb.Foodzy.Repository.UserRepositories;
import com.EcommerceWeb.Foodzy.ServicesInterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {

        Order order = this.orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "order",
                                "orderId",
                                requestDto.getOrderId()
                        )
                );

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentMethod(requestDto.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        if (requestDto.getPaymentMethod().name().equals("COD")){

            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setTransactionId(null);
        }else{

            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            payment.setTransactionId(
                    UUID.randomUUID().toString()
            );
        }

        Payment savedPayment =
                this.paymentRepository.save(payment);

        return this.paymentMapper.toResponse(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {

        Payment payment = this.paymentRepository.findByOrderOrderId(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("payment", "orderId", orderId));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        if (!payment.getOrder().getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Access Denied");
        }

        return this.paymentMapper.toResponse(payment);
    }
}
