package com.EcommerceWeb.Foodzy.Config;

import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        ApiResponse<Void> apiResponse =
                new ApiResponse(
                        "You don't have permission",
                        ResponseStatus.FORBIDDEN,
                        null
                );


        ObjectMapper mapper = new ObjectMapper();


        response.getWriter()
                .write(
                        mapper.writeValueAsString(apiResponse)
                );

    }
}
