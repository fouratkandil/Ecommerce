package com.EcommerceApp.JGFE.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Long discount;

    private Date expirationDate;
}
