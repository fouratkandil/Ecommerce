package com.EcommerceApp.JGFE.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.EcommerceApp.JGFE.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="product")
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private Long price;

    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;


    public ProductDto getDto(){
        ProductDto productDto = new ProductDto();

        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setByteImg(img);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;
    }
}
