package com.EcommerceApp.JGFE.services.customer.wishlist;

import java.util.List;

import com.EcommerceApp.JGFE.dto.WhishlistDto;

public interface WishlistService {
    WhishlistDto addProductToWishlist(WhishlistDto whishlistDto);
    List<WhishlistDto> findAllByUserId(Long userId);
}
