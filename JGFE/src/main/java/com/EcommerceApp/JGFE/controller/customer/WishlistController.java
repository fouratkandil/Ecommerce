package com.EcommerceApp.JGFE.controller.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.WhishlistDto;
import com.EcommerceApp.JGFE.services.customer.wishlist.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WhishlistDto whishlistDto) {
        WhishlistDto postedWishlistDto = wishlistService.addProductToWishlist(whishlistDto);

        if (postedWishlistDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postedWishlistDto);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WhishlistDto>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.findAllByUserId(userId));
    }
}
