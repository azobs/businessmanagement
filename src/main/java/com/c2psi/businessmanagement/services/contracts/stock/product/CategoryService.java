package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategory(PointofsaleDto posDto);
    CategoryDto saveCategory(CategoryDto catDto);
    CategoryDto findCategoryByCode(String catCode, PointofsaleDto posDto);
    CategoryDto findCategoryById(Long catId);
    Boolean categoryIsUnique(String catCode, PointofsaleDto posDto);
    Boolean isCatageryEmpty(CategoryDto catDto);
    Boolean isCatageryUnique(String catCode, PointofsaleDto posDto);
    Boolean deleteCategoryById(Long catId);
}
