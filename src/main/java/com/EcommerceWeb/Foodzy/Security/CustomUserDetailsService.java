package com.EcommerceWeb.Foodzy.Security;

import com.EcommerceWeb.Foodzy.Entities.User;
import com.EcommerceWeb.Foodzy.Repository.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                User user = userRepositories.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + username)
                );

        return new CustomUserDetails(user);
    }
}
