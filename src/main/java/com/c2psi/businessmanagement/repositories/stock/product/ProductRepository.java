package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCat.id=:catId ORDER BY prod.prodName ASC ")
    Optional<List<Product>> findAllProductOfCategory(Long catId);
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCat.id=:catId ORDER BY prod.prodName ASC ")
    Optional<Page<Product>> findPageofProductOfCategory(Long catId, Pageable pageable);
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCode=:prodCode AND prod.prodPosId=:posId")
    Optional<Product> findProductInPointofsaleByCode(@Param("prodCode") String prodCode, @Param("posId") Long posId);
    Optional<Product> findProductById(Long prodId);
    @Query("SELECT prod FROM Product prod WHERE prod.prodPosId=:posId ORDER BY prod.prodCat.catName ASC, prod.prodName ASC")
    Optional<List<Product>> findAllProductInPos(@Param("posId") Long posId);
    @Query("SELECT prod FROM Product prod WHERE prod.prodPosId=:posId ORDER BY prod.prodCat.catName ASC, prod.prodName ASC")
    Optional<Page<Product>> findPageofProductInPos(@Param("posId") Long posId, Pageable pageable);
    @Query("SELECT prod FROM Product  prod WHERE prod.prodName LIKE :sample AND prod.prodPosId=:posId ORDER BY prod.prodName ASC")
    Optional<Page<Product>> findAllProductByProdNameInPosContaining(Long posId, String sample, Pageable pageable);
}
