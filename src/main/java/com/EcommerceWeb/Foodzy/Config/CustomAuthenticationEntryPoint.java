package com.EcommerceWeb.Foodzy.Config;

import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        ApiResponse<Void> apiResponse =
                new ApiResponse(
                        "Authentication required",
                        ResponseStatus.UNAUTHORIZED,
                        null
                );


        ObjectMapper mapper = new ObjectMapper();

        response.getWriter()
                .write(
                        mapper.writeValueAsString(apiResponse)
                );
    }
}
