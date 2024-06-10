package com.EcommerceApp.JGFE.services.admin.category;

import java.util.List;

import com.EcommerceApp.JGFE.dto.CatRequest;
import com.EcommerceApp.JGFE.entity.Category;

public interface CategoryService {
     Category createCategory(CatRequest catRequest);
     List<Category> getAllCategories();
}
