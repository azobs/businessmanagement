package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.CategoryParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryParentCategoryRepository extends JpaRepository<CategoryParentCategory, Long> {
    Optional<CategoryParentCategory> findCategoryParentCategoryById(Long catParentCatId);

    @Query("SELECT catparcat FROM CategoryParentCategory  catparcat WHERE ((catparcat.childCategory.id=:catChildCat AND catparcat.parentCategory.id=:catParentCat) OR (catparcat.childCategory.id=:catParentCat AND catparcat.parentCategory.id=:catChildCat))")
    Optional<CategoryParentCategory> findRelationBetween(Long catChildCat, Long catParentCat);
}
