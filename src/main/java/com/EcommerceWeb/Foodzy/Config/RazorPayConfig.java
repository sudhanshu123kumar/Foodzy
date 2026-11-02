package com.EcommerceWeb.Foodzy.Config;

import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Bean
    public RazorpayClient razorpayClient() throws Exception {

        // CHANGED: RazorpayClient ko Spring Bean banaya.
        // WHY: PaymentServiceImpl mein dependency inject kar sakein.
        return new RazorpayClient(keyId, keySecret);
    }

}
