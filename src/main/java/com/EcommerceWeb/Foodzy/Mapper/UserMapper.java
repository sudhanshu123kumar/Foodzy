package com.EcommerceWeb.Foodzy.Mapper;

import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.Entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserRegisterDto userRegisterDto) {

        return this.modelMapper.map(
                userRegisterDto,
                User.class
        );
    }

    public UserDtoResponse toResponse(User user){

        return this.modelMapper.map(user, UserDtoResponse.class);
    }
}
