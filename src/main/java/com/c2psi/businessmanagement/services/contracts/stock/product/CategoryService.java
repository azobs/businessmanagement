package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    Boolean isCategoryUniqueInPos(String catCode, Long posId);

    CategoryDto findCategoryById(Long catId);

    CategoryDto findCategoryParentof(Long catId);

    List<CategoryDto> findAscandantCategoryof(Long catId);

    List<CategoryDto> findChildCategoryOf(Long catId);

    List<CategoryDto> findAllCategoryInPointofsale(Long posId);

    Page<CategoryDto> findPageCategoryInPointofsale(Long posId, int pagenum, int pagesize);

    Page<CategoryDto> findPageByCatNameInPosContaining(Long posId, String sample, int pagenum, int pagesize);

    Boolean isCategoryDeleteable(Long catId);

    Boolean deleteCategoryById(Long catId);

    Boolean deleteCategoryByCatCode(String catCode, Long posId);


}
