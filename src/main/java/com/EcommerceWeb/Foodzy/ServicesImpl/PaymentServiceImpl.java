package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.PaymentRequestDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentResponseDto;
import com.EcommerceWeb.Foodzy.Dto.PaymentVerificationDto;
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
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private UserRepositories userRepositories;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {

        if (requestDto.getPaymentMethod() == null) {
            throw new IllegalArgumentException(
                    "Payment Method is required"
            );
        }

        // Step 1: Order find karo
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "order",
                                "orderId",
                                requestDto.getOrderId()
                        )
                );

        // Step 2: Existing payment check karo
        Payment payment = paymentRepository
                .findByOrderOrderId(requestDto.getOrderId())
                .orElse(null);

        if (payment != null) {

            // Agar payment already successful hai
            if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
                throw new IllegalArgumentException(
                        "Payment already completed for this order"
                );
            }

            // Agar existing payment ka method alag hai
            if (payment.getPaymentMethod() != requestDto.getPaymentMethod()) {
                throw new IllegalArgumentException(
                        "Payment method does not match existing payment"
                );
            }

            // Agar Razorpay order pehle hi create ho chuka hai
            if (payment.getGatewayOrderId() != null) {
                PaymentResponseDto response =
                        paymentMapper.toResponse(payment);

                response.setRazorpayKeyId(keyId);
                return response;
            }

        } else {

            // Step 3: Naya payment record banao
            payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalPrice());
            payment.setPaymentMethod(requestDto.getPaymentMethod());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setTransactionId(null);
        }

        // Step 4: COD ka flow
        if (requestDto.getPaymentMethod()
                == com.EcommerceWeb.Foodzy.Enum.PaymentMethod.COD) {

            payment.setPaymentStatus(PaymentStatus.PENDING);

            Payment savedPayment = paymentRepository.save(payment);

            return paymentMapper.toResponse(savedPayment);
        }

        // Step 5: Razorpay order create karo
        try {
            JSONObject orderRequest = new JSONObject();

            long amountInPaise = BigDecimal
                    .valueOf(order.getTotalPrice())
                    .multiply(BigDecimal.valueOf(100))
                    .longValueExact();

            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put(
                    "receipt",
                    "order_" + order.getOrderId()
            );

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(orderRequest);

            // Step 6: Razorpay order ID existing payment mein set karo
            payment.setGatewayOrderId(
                    razorpayOrder.get("id")
            );

            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setTransactionId(null);

            // Step 7: Payment save/update karo
            Payment savedPayment = paymentRepository.save(payment);

            // Step 8: Response DTO banao
            PaymentResponseDto response =
                    paymentMapper.toResponse(savedPayment);

            response.setRazorpayKeyId(keyId);

            return response;

        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Unable to create Razorpay order: "
                            + ex.getMessage(),
                    ex
            );
        }
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

    @Override
    @Transactional
    public PaymentResponseDto verifyPayment(
            PaymentVerificationDto request) {

        try {
            Payment payment = paymentRepository
                    .findByGatewayOrderId(request.getRazorpayOrderId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Payment order not found"));

            if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
                if (Objects.equals(
                        payment.getTransactionId(),
                        request.getRazorpayPaymentId())) {
                    PaymentResponseDto response =
                            paymentMapper.toResponse(payment);

                    response.setRazorpayKeyId(keyId);

                    return response;
                }

                throw new IllegalStateException(
                        "Payment is already completed");
            }

            JSONObject attributes = new JSONObject();
            attributes.put(
                    "razorpay_order_id",
                    request.getRazorpayOrderId());
            attributes.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId());
            attributes.put(
                    "razorpay_signature",
                    request.getRazorpaySignature());

            Utils.verifyPaymentSignature(attributes, keySecret);

            com.razorpay.Payment gatewayPayment =
                    razorpayClient.payments.fetch(
                            request.getRazorpayPaymentId());

            if (!request.getRazorpayOrderId().equals(
                    gatewayPayment.get("order_id"))) {
                throw new IllegalArgumentException(
                        "Gateway order mismatch");
            }

            if (!"captured".equals(gatewayPayment.get("status"))) {
                throw new IllegalStateException(
                        "Payment is not captured");
            }

            long expectedPaise =  BigDecimal.valueOf(payment.getAmount())
                    .multiply(BigDecimal.valueOf(100))
                    .longValueExact();

            long actualPaise = ((Number) gatewayPayment.get("amount"))
                    .longValue();

            if (expectedPaise != actualPaise ||
                    !"INR".equals(gatewayPayment.get("currency"))) {
                throw new IllegalArgumentException(
                        "Payment amount or currency mismatch");
            }

            if (paymentRepository.existsByTransactionId(
                    request.getRazorpayPaymentId())) {
                throw new IllegalStateException(
                        "Transaction already used");
            }

            payment.setTransactionId(
                    request.getRazorpayPaymentId());
            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            Payment savedPayment = paymentRepository.save(payment);

            PaymentResponseDto response =
                    paymentMapper.toResponse(savedPayment);

            response.setRazorpayKeyId(keyId);

            return response;

        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Payment verification failed", ex);
        }
    }
}
