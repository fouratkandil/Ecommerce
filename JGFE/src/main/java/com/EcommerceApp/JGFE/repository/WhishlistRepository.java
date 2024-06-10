package com.EcommerceApp.JGFE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcommerceApp.JGFE.entity.Whishlist;

@Repository
public interface WhishlistRepository extends JpaRepository<Whishlist, Long>{
    List<Whishlist> findAllByUserId(Long userId); 
}
