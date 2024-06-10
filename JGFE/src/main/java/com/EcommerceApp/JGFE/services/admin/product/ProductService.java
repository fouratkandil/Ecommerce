package com.EcommerceApp.JGFE.services.admin.product;

import java.io.IOException;
import java.util.List;


import com.EcommerceApp.JGFE.dto.ProductDto;


public interface ProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> allproducts();
    List<ProductDto> findProductByName(String name);
    boolean deleteProduct(Long id);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;
}
