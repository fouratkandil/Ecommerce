package com.EcommerceApp.JGFE.controller.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.AddProducttoCartDto;
import com.EcommerceApp.JGFE.dto.OrderDto;
import com.EcommerceApp.JGFE.dto.PlaceOrderDto;
import com.EcommerceApp.JGFE.enums.exceptions.ValidationException;
import com.EcommerceApp.JGFE.services.customer.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProducttoCartDto addProducttoCartDto) {
        return cartService.addProductToCart(addProducttoCartDto);
    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = cartService.getCartByUserId(userId);
        if (orderDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            OrderDto orderDto = cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(orderDto);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProducttoCartDto addProducttoCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProducttoCartDto));
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProducttoCartDto addProducttoCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProducttoCartDto));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrers(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getMyPlacedOrder(userId));
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeProductFromCart(@RequestBody AddProducttoCartDto addProducttoCartDto) {
        return cartService.removeFromCart(addProducttoCartDto);
    }

}
