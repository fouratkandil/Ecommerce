package com.EcommerceApp.JGFE.services.customer.review;

import java.io.IOException;

import com.EcommerceApp.JGFE.dto.OrderedProductsResponseDto;
import com.EcommerceApp.JGFE.dto.ReviewDto;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
