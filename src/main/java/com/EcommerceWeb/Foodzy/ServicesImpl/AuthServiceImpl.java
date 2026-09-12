package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.JwtResponseDto;
import com.EcommerceWeb.Foodzy.Dto.LoginRequestDto;
import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.Entities.User;
import com.EcommerceWeb.Foodzy.Enum.Role;
import com.EcommerceWeb.Foodzy.Exceptions.DuplicateResourceException;
import com.EcommerceWeb.Foodzy.Mapper.UserMapper;
import com.EcommerceWeb.Foodzy.Repository.UserRepositories;
import com.EcommerceWeb.Foodzy.Security.JwtTokenHelper;
import com.EcommerceWeb.Foodzy.ServicesInterface.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public JwtResponseDto login(LoginRequestDto requestDto) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )

        );

        User user = this.userRepositories.findByEmail(
                        requestDto.getEmail()
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        String token =
                jwtTokenHelper.generateToken(requestDto.getEmail());



        return new JwtResponseDto(

                token,
                "Bearer",
                user.getUserId(),
                user.getName(),
                user.getEmail()

        );
    }

    @Override
    public UserDtoResponse register(UserRegisterDto userRegisterDto) {

            if(this.userRepositories.existsByEmail(userRegisterDto.getEmail())){

                throw new DuplicateResourceException(
                        "Email already exists"
                );
            }

        if(this.userRepositories.existsByPhoneNumber(
                userRegisterDto.getPhoneNumber()
        )){

            throw new DuplicateResourceException(
                    "Phone number already exists"
            );

        }
        User user = this.userMapper.toEntity(userRegisterDto);

        user.setPassword(
                passwordEncoder.encode(userRegisterDto.getPassword())
        );

        user.setRole(Role.ROLE_USER);

        User savedUser = this.userRepositories.save(user);

        return this.userMapper.toResponse(savedUser);
    }
}
