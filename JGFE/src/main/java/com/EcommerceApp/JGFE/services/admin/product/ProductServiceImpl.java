package com.EcommerceApp.JGFE.services.admin.product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.ProductDto;
import com.EcommerceApp.JGFE.entity.Category;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.repository.CategoryRepository;
import com.EcommerceApp.JGFE.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    public List<ProductDto> allproducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> findProductByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()){
            return optionalProduct.get().getDto();
        }
        return null;
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException{
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());

        if(optionalProduct.isPresent() && optionalCategory.isPresent()){
            Product product = optionalProduct.get();

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setCategory(optionalCategory.get());

            if(productDto.getImg() != null){
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();
        }
        else{
            return null;
        }
    }



}
