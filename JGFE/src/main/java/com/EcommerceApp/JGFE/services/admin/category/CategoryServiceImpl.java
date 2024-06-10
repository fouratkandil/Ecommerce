package com.EcommerceApp.JGFE.services.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.CatRequest;
import com.EcommerceApp.JGFE.entity.Category;
import com.EcommerceApp.JGFE.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CatRequest catRequest) {
        Category category = new Category();
        category.setName(catRequest.getName());
        category.setDescription(catRequest.getDescription());
        return categoryRepository.save(category);
         
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
