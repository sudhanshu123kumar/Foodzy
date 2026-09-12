package com.EcommerceWeb.Foodzy.ServicesInterface;

import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.Dto.UserUpdateDto;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;

import java.util.List;

public interface UserService {

    UserDtoResponse updateUser(UserUpdateDto userUpdateDto, Long userId);

    UserDtoResponse getUserById(Long userId);

    PageResponse<UserDtoResponse> getAllUser(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    );

    void deleteUser(Long userId);

    UserDtoResponse getCurrentUser();

    UserDtoResponse updateMyProfile(UserUpdateDto userUpdateDto);
}
