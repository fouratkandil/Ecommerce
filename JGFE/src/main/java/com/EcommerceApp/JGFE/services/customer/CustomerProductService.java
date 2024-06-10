package com.EcommerceApp.JGFE.services.customer;

import java.util.List;

import com.EcommerceApp.JGFE.dto.ProductDetailDto;
import com.EcommerceApp.JGFE.dto.ProductDto;

public interface CustomerProductService {
    List<ProductDto> allproducts();
    List<ProductDto> searchProductByName(String name);
    ProductDetailDto getProductDetailById(Long productId);
}
