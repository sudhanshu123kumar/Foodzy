package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.Dto.ProductSimpleResponseDto;
import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.Dto.UserUpdateDto;
import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.ServicesInterface.UserService;
import com.EcommerceWeb.Foodzy.Utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(
        name = "User Module",
        description = "APIs for User Profile and Admin User Management"
)
public class UserController {


    @Autowired
    private UserService userService;

//      user profile

    @Operation(
            summary = "Get Current User Profile",
            description = "Returns details of currently authenticated user."
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getCurrentUser(){

        UserDtoResponse response =
                userService.getCurrentUser();

        ApiResponse<UserDtoResponse> apiResponse =
                new ApiResponse<>(
                        "my profile open successfully",
                        ResponseStatus.SUCCESS,
                        response
                );

        return new ResponseEntity<ApiResponse<UserDtoResponse>>(apiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Update My Profile",
            description = "Updates profile information of logged-in user."
    )
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateMyProfile(
            @Valid @RequestBody UserUpdateDto userUpdateDto
    ){

        UserDtoResponse response =
                userService.updateMyProfile(userUpdateDto);

        ApiResponse<UserDtoResponse> apiResponse =
                new ApiResponse<>(
                        "my profile updated successfully",
                        ResponseStatus.SUCCESS,
                        response
                );

        return new ResponseEntity<ApiResponse<UserDtoResponse>>(apiResponse, HttpStatus.OK);
    }


//      admin user management

    @Operation(
            summary = "Get User By ID",
            description = "Admin can fetch user details using user ID."
    )
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUserById(@PathVariable Long userId){

       UserDtoResponse user =  this.userService.getUserById(userId);

        ApiResponse<UserDtoResponse> apiResponse =
                new ApiResponse<>(
                        "user find successfully",
                        ResponseStatus.SUCCESS,
                        user
                );

       return new ResponseEntity<ApiResponse<UserDtoResponse>>(apiResponse,HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Users",
            description = "Admin can view all users with pagination and sorting."
    )
    @GetMapping("/")
    public ResponseEntity<ApiResponse<PageResponse<UserDtoResponse>>> getAllUser(
           @RequestParam (value = "pageSize", defaultValue = AppConstants.PAGE_NUMBER, required = false)
           Integer pageNumber,
           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
           Integer pageSize,
           @RequestParam(value = "sortBy", defaultValue = AppConstants.USER_SORT_BY, required = false)
           String sortBy,
           @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
           String sortDir
    ){

        PageResponse<UserDtoResponse> users = this.userService.getAllUser(
                pageNumber,
                pageSize,
                sortBy,
                sortDir
        );

        ApiResponse<PageResponse<UserDtoResponse>> apiResponse =
                new ApiResponse<>(
                        users.getItems().isEmpty()
                                ?"No Users found"
                                :"Users fetch successfully",
                        ResponseStatus.SUCCESS,
                        users
                );
        return new ResponseEntity<ApiResponse<PageResponse<UserDtoResponse>>>(apiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete User",
            description = "Admin can delete a user using user ID."
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable Long userId){

        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse<Void>>(new ApiResponse("user deleted successFully", ResponseStatus.SUCCESS, null), HttpStatus.OK);
    }


    @Operation(
            summary = "Update User",
            description = "Admin can update user details using user ID."
    )
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(@RequestBody UserUpdateDto userUpdateDto,
                                                      @PathVariable Long userId){

        UserDtoResponse updateUser = this.userService.updateUser(userUpdateDto,userId);

        ApiResponse<UserDtoResponse> apiResponse =
                new ApiResponse<>(
                        "user find successfully",
                        ResponseStatus.SUCCESS,
                        updateUser
                );

        return new ResponseEntity<ApiResponse<UserDtoResponse>>(apiResponse,HttpStatus.OK);
    }
}
