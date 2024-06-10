package com.EcommerceApp.JGFE.entity;

import com.EcommerceApp.JGFE.dto.UserCrudDto;
import com.EcommerceApp.JGFE.enums.UserRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    public UserCrudDto getDto(){
        UserCrudDto userCrudDto = new UserCrudDto();
        userCrudDto.setId(id);
        userCrudDto.setName(name);
        userCrudDto.setEmail(email);
        userCrudDto.setPassword(password);
        return userCrudDto;
    }

}
