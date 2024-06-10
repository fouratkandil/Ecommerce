package com.EcommerceApp.JGFE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcommerceApp.JGFE.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}