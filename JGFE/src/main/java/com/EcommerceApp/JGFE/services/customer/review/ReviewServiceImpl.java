package com.EcommerceApp.JGFE.services.customer.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.OrderedProductsResponseDto;
import com.EcommerceApp.JGFE.dto.ProductDto;
import com.EcommerceApp.JGFE.dto.ReviewDto;
import com.EcommerceApp.JGFE.entity.CartItems;
import com.EcommerceApp.JGFE.entity.Order;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.entity.Review;
import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.repository.OrderRepository;
import com.EcommerceApp.JGFE.repository.ProductRepository;
import com.EcommerceApp.JGFE.repository.ReviewRepository;
import com.EcommerceApp.JGFE.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        OrderedProductsResponseDto oResponseDto = new OrderedProductsResponseDto();

        if (optionalOrder.isPresent()) {
            oResponseDto.setOrderAmount(optionalOrder.get().getAmount());

            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartItems cartItems : optionalOrder.get().getCartItems()) {
                ProductDto productDto = new ProductDto();

                productDto.setId(cartItems.getProduct().getId());
                productDto.setName(cartItems.getProduct().getName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(cartItems.getQuantity());

                productDto.setByteImg(cartItems.getProduct().getImg());

                productDtoList.add(productDto);
            }
            oResponseDto.setProductDtoList(productDtoList);
        }
        return oResponseDto;
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
        Optional<user> optionalUser = userRepository.findById(reviewDto.getUserId());

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
            Review review = new Review();

            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUser(optionalUser.get());
            review.setProduct(optionalProduct.get());
            review.setImg(reviewDto.getImg().getBytes());

            return reviewRepository.save(review).getDto();

        }
        return null;
    }
}
