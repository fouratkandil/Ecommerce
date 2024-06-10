package com.EcommerceApp.JGFE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcommerceApp.JGFE.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findAllByProductId(Long productId);
}
