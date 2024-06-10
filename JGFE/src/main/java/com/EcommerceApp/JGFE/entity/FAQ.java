package com.EcommerceApp.JGFE.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.EcommerceApp.JGFE.dto.FAQDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public FAQDto getFAQDto(){
        FAQDto faqDto = new FAQDto();

        faqDto.setId(id);
        faqDto.setAnswer(answer);
        faqDto.setQuestion(question);
        faqDto.setProductId(product.getId());

        return faqDto;
    }
    
}
