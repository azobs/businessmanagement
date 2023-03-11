package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryParentCategoryDto;

public interface CategoryParentCategoryService {
    CategoryParentCategoryDto saveParentCategory(CategoryParentCategoryDto parentCategoryDto);

    Boolean isCategoryParentCategoryUnique(Long childCatId, Long parentCatId);

    CategoryParentCategoryDto findCategoryParentCategoryById(Long catPatCatId);

    CategoryDto findParentCategoryOf(CategoryDto categoryDto);

}
