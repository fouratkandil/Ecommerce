package com.EcommerceApp.JGFE.controller.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.ProductDetailDto;
import com.EcommerceApp.JGFE.dto.ProductDto;
import com.EcommerceApp.JGFE.services.customer.CustomerProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> allproducts(){
        List<ProductDto> productDtos = customerProductService.allproducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> findProductByName(@PathVariable String name){
        List<ProductDto> productDtos = customerProductService.searchProductByName(name);
        return ResponseEntity.ok(productDtos);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId){
        ProductDetailDto productDetailDto = customerProductService.getProductDetailById(productId);
        if(productDetailDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailDto);
    }
    
}
