package com.EcommerceApp.JGFE.controller.customer;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.OrderedProductsResponseDto;
import com.EcommerceApp.JGFE.dto.ReviewDto;
import com.EcommerceApp.JGFE.services.customer.review.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewDto2 = reviewService.giveReview(reviewDto);

        if (reviewDto2 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto2);
    }
}
