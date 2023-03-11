package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Pointofsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT cat FROM Category  cat WHERE cat.catCode=:catCode AND cat.catPos.id=:posId")
    Optional<Category> findCategoryInPointofsaleByCode(@Param("catCode") String catCode, @Param("posId") Long posId);

    Optional<Category> findCategoryById(Long id);
}
