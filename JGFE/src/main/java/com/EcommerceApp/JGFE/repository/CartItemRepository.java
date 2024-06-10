package com.EcommerceApp.JGFE.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcommerceApp.JGFE.entity.CartItems;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long>{
    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long long1, Long long2, Long userId);
}
