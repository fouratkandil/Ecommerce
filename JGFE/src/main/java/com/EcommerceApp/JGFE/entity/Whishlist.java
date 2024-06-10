package com.EcommerceApp.JGFE.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.EcommerceApp.JGFE.dto.WhishlistDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Whishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private user user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public WhishlistDto getWishlistDto() {
        WhishlistDto whishlistDto = new WhishlistDto();

        whishlistDto.setId(id);
        whishlistDto.setProductId(product.getId());
        whishlistDto.setUserId(user.getId());
        whishlistDto.setReturnedImg(product.getImg());
        whishlistDto.setProductName(product.getName());
        whishlistDto.setProductDescription(product.getDescription());
        whishlistDto.setPrice(product.getPrice());

        return whishlistDto;
    }
}
