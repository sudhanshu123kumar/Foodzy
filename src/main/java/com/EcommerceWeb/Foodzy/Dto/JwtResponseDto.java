package com.EcommerceWeb.Foodzy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {

    private String token;

    private String tokenType = "Bearer";

    private Long userId;

    private String name;

    private String email;

}
