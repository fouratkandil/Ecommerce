package com.EcommerceApp.JGFE.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.EcommerceApp.JGFE.dto.ReviewDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;

    @Lob
    private String Description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private user user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public ReviewDto getDto() {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(id);
        reviewDto.setRating(rating);
        reviewDto.setDescription(Description);
        reviewDto.setReturnedImg(img);
        reviewDto.setUserId(user.getId());
        reviewDto.setProductId(product.getId());
        reviewDto.setUsername(user.getName());

        return reviewDto;
    }
}
