package com.EcommerceApp.JGFE.dto;

import com.EcommerceApp.JGFE.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
    private Long Id;
    private String email;
    private String name;
    private UserRole role;
}
