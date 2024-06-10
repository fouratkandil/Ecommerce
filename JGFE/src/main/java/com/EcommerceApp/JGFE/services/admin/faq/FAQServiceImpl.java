package com.EcommerceApp.JGFE.services.admin.faq;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.FAQDto;
import com.EcommerceApp.JGFE.entity.FAQ;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.repository.FAQRepository;
import com.EcommerceApp.JGFE.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{
    private final FAQRepository faqRepository;
    private final ProductRepository productRepository;

    public FAQDto potFAQ(Long productId, FAQDto faqDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()){
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
}
