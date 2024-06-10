package com.EcommerceApp.JGFE.services.customer.wishlist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.WhishlistDto;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.entity.Whishlist;
import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.repository.ProductRepository;
import com.EcommerceApp.JGFE.repository.UserRepository;
import com.EcommerceApp.JGFE.repository.WhishlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService{
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final WhishlistRepository whishlistRepository;


    public WhishlistDto addProductToWishlist(WhishlistDto whishlistDto){
        Optional<Product> optionalProduct = productRepository.findById(whishlistDto.getProductId());
        Optional<user> optionalUser = userRepository.findById(whishlistDto.getUserId());

        if(optionalProduct.isPresent() && optionalUser.isPresent()){
            Whishlist whishlist = new Whishlist();

            whishlist.setProduct(optionalProduct.get());
            whishlist.setUser(optionalUser.get());

            return whishlistRepository.save(whishlist).getWishlistDto();
        }
        return null;
    }

    public List<WhishlistDto> findAllByUserId(Long userId){
        return whishlistRepository.findAllByUserId(userId).stream().map(Whishlist::getWishlistDto).collect(Collectors.toList());
    }
    
}
