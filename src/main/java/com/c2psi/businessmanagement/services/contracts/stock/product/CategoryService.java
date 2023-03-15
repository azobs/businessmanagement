package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategory();
    List<CategoryDto> findAllCategoryInPointofsale(Long posId);
    CategoryDto saveCategory(CategoryDto catDto);
    CategoryDto updateCategory(CategoryDto catDto);
    CategoryDto findCategoryByCode(String catCode, PointofsaleDto posDto);
    CategoryDto findCategoryById(Long catId);
    Boolean isCatageryEmpty(CategoryDto catDto);
    Boolean isCatageryUnique(String catCode, PointofsaleDto posDto);
    Boolean deleteCategoryById(Long catId);

    Boolean deleteCategoryByCatCode(String catCode, Long posId);

    Page<CategoryDto> pageofExistingCategory(int pagenum, int pagesize);

    Page<CategoryDto> pageofCategoryInPos(Long posId, int pagenum, int pagesize);
}
