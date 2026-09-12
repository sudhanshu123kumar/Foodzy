package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.Dto.UserDtoResponse;
import com.EcommerceWeb.Foodzy.Dto.UserRegisterDto;
import com.EcommerceWeb.Foodzy.Dto.UserUpdateDto;
import com.EcommerceWeb.Foodzy.Entities.User;
import com.EcommerceWeb.Foodzy.Enum.Role;
import com.EcommerceWeb.Foodzy.Exceptions.ResourceNotFoundException;
import com.EcommerceWeb.Foodzy.Mapper.UserMapper;
import com.EcommerceWeb.Foodzy.Payload.PageResponse;
import com.EcommerceWeb.Foodzy.Repository.UserRepositories;
import com.EcommerceWeb.Foodzy.ServicesInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDtoResponse updateUser(UserUpdateDto userUpdateDto, Long userId) {
        User user = this.userRepositories.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        user.setName(userUpdateDto.getName());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setAddress(userUpdateDto.getAddress());

        User updatedUser = this.userRepositories.save(user);
        return this.userMapper.toResponse(updatedUser);
    }

    @Override
    public UserDtoResponse getUserById(Long userId) {
        User user = this.userRepositories.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.userMapper.toResponse(user);
    }

    @Override
    public PageResponse<UserDtoResponse> getAllUser(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );

      Page<User> users =  this.userRepositories.findAll(pageable);

      List<UserDtoResponse> userDtos = users.getContent()
              .stream().map((user) -> { UserDtoResponse response = this.userMapper.toResponse(user);

                  return response;
              }).collect(Collectors.toList());

      return PageResponse.<UserDtoResponse>builder()
              .productData(userDtos)
              .pageNumber(users.getNumber())
              .pageSize(users.getSize())
              .totalElements(users.getTotalElements())
              .totalPages(users.getTotalPages())
              .lastPage(users.isLast())
              .build();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepositories.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        this.userRepositories.delete(user);
    }

    @Override
    public UserDtoResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        return this.userMapper.toResponse(user);
    }

    @Override
    public UserDtoResponse updateMyProfile(UserUpdateDto userUpdateDto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepositories.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );

        user.setName(userUpdateDto.getName());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setAddress(userUpdateDto.getAddress());

        User updatedUser = this.userRepositories.save(user);

        return this.userMapper.toResponse(updatedUser);
    }

}
