package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT cat FROM Category  cat WHERE cat.catCode=:catCode AND cat.catPosId=:posId")
    Optional<Category> findCategoryInPointofsaleByCode(@Param("catCode") String catCode, @Param("posId") Long posId);

    Optional<Category> findCategoryById(Long id);

    @Query("SELECT cat FROM Category  cat WHERE cat.catPosId=:posId ORDER BY cat.catName ASC ")
    Optional<List<Category>> findAllCategoryInPointofsale(@Param("posId") Long posId);

    @Query("SELECT cat FROM Category  cat WHERE cat.catPosId=:posId ORDER BY cat.catName ASC ")
    Optional<Page<Category>> findAllCategoryInPointofsale(@Param("posId") Long posId, Pageable pageable);

    @Query("SELECT cat FROM Category cat WHERE cat.categoryParentId=:catId ORDER BY cat.catName ASC ")
    Optional<List<Category>> findChildCategoryOf(@Param("catId") Long catId);

    @Query("SELECT cat FROM Category  cat WHERE cat.catName LIKE :sample AND cat.catPosId=:posId ORDER BY cat.catName ASC")
    Optional<Page<Category>> findAllByCatNameInPosContaining(@Param("posId") Long posId, @Param("sample") String sample, Pageable pageable);




}
