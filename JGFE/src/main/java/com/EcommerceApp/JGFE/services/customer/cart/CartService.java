package com.EcommerceApp.JGFE.services.customer.cart;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.EcommerceApp.JGFE.dto.AddProducttoCartDto;
import com.EcommerceApp.JGFE.dto.OrderDto;
import com.EcommerceApp.JGFE.dto.PlaceOrderDto;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProducttoCartDto addProducttoCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity(AddProducttoCartDto addProducttoCartDto);

    OrderDto decreaseProductQuantity(AddProducttoCartDto addProducttoCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrder(Long userId);

    OrderDto searchOrderByTrackingId(UUID trackingId);

    ResponseEntity<?> removeFromCart(AddProducttoCartDto addProducttoCartDto);
}
