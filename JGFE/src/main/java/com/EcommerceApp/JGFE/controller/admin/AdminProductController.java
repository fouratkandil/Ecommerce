package com.EcommerceApp.JGFE.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.FAQDto;
import com.EcommerceApp.JGFE.dto.ProductDto;
import com.EcommerceApp.JGFE.services.admin.faq.FAQService;
import com.EcommerceApp.JGFE.services.admin.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    private final FAQService faqService;


    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException{
        ProductDto productDto2 = productService.addProduct(productDto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto2); 
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> allproducts(){
        List<ProductDto> productDtos = productService.allproducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> findProductByName(@PathVariable String name){
        List<ProductDto> productDtos = productService.findProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);

        if(deleted){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }


    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @RequestBody FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.potFAQ(productId, faqDto));
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProuctById(@PathVariable Long productId){
        ProductDto productDto = productService.getProductById(productId);
        
        if(productDto != null){
            return ResponseEntity.ok(productDto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException{
        ProductDto updatedDto = productService.updateProduct(productId, productDto);

        if(updatedDto != null){
            return ResponseEntity.ok(updatedDto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    
}
