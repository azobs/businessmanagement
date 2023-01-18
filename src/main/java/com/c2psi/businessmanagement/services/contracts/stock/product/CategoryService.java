package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategory(PointofsaleDto posDto);
    CategoryDto saveCategory(CategoryDto catDto);
    CategoryDto findCategory(String catCode, PointofsaleDto posDto);
    CategoryDto findCategory(Long catId);
    Boolean categoryIsUnique(String catCode, PointofsaleDto posDto);
    Boolean isCatageryEmpty(CategoryDto catDto);
    Boolean deleteCategoryById(Long catId);
}
