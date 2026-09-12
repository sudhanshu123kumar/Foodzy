package com.EcommerceWeb.Foodzy.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {


    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 20, message = "Name must be between 4 and 20 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;


    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be between 10 digits")
    private String phoneNumber;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;


}
