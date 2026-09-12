package com.EcommerceWeb.Foodzy.Entities;

import com.EcommerceWeb.Foodzy.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id")
     private Long userId;


     @Column(nullable = false, length = 20)
     private String name;

     @Column(nullable = false, unique = true, length = 50)
     private String email;


     @Column(nullable = false)
     private String password;

     @Column(unique = true, length = 10)
     private String phoneNumber;

     @Column(length = 255)
     private String address;

     @CreationTimestamp
     @Column(updatable = false)
     private LocalDateTime createdAt;

     @UpdateTimestamp
     private LocalDateTime updatedAt;

     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private Role role;

}
