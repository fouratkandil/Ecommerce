package com.EcommerceApp.JGFE.dto;

import lombok.Data;

@Data
public class UserCrudDto {
    private Long Id;
    private String email;
    private String name;
    private String password;
}
