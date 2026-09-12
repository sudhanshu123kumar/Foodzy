package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.JwtResponseDto;
import com.EcommerceWeb.Foodzy.Dto.LoginRequestDto;
import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.ServicesInterface.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

@Tag(
        name = "Authentication Module",
        description = "APIs for User Registration and Login"
)
public class AuthController {


    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Register New User",
            description = "Creates a new user account and returns the registered user details."
    )
    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> register(
            @Valid @RequestBody UserRegisterDto userRegisterDto
    ) {

        UserDtoResponse response =
                authService.register(userRegisterDto);


        return new ResponseEntity<UserDtoResponse>(
                response,
                HttpStatus.CREATED
        );
    }


    @Operation(
            summary = "User Login",
            description = "Authenticates the user and returns a JWT access token."
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {

        JwtResponseDto response =
                authService.login(loginRequestDto);


        return new ResponseEntity<JwtResponseDto>(response,HttpStatus.OK);
    }


}
