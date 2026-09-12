package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.JwtResponseDto;
import com.EcommerceWeb.Foodzy.Dto.LoginRequestDto;
import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;

public interface AuthService {

    UserDtoResponse register(UserRegisterDto userRegisterDto);

    JwtResponseDto login(LoginRequestDto loginRequestDto);
}
