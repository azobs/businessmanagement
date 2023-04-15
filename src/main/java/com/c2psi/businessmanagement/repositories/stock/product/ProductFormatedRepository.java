package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.ProductFormated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductFormatedRepository
        extends JpaRepository<ProductFormated, Long> {
    Optional<ProductFormated> findProductFormatedById(Long pfId);
    @Query("SELECT pf FROM ProductFormated  pf WHERE pf.pfFormat.id=:formatId AND pf.pfProduct.id=:prodId")
    Optional<ProductFormated> findProductFormatedInPosWith(@Param("prodId") Long prodId, @Param("formatId") Long formatId);
    @Query("SELECT pf FROM ProductFormated pf WHERE pf.pfProduct.prodPos.id=:posId")
    Optional<List<ProductFormated>> findAllProductFormatedInPos(@Param("posId") Long posId);

    @Query("SELECT pf FROM ProductFormated pf WHERE pf.pfProduct.prodPos.id=:posId ORDER BY pf.pfProduct.prodName ASC")
    Optional<Page<ProductFormated>> findPageofProductFormatedInPos(@Param("posId") Long posId, Pageable pageable);
}
