package com.EcommerceApp.JGFE.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.ProductDetailDto;
import com.EcommerceApp.JGFE.dto.ProductDto;
import com.EcommerceApp.JGFE.entity.FAQ;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.entity.Review;
import com.EcommerceApp.JGFE.repository.FAQRepository;
import com.EcommerceApp.JGFE.repository.ProductRepository;
import com.EcommerceApp.JGFE.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    private final ProductRepository productRepository;

    private final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;

    public List<ProductDto> allproducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> searchProductByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetailById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);

            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

            return productDetailDto;
        }
        return null;
    }
    
}
