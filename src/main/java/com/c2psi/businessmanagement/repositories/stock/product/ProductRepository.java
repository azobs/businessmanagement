package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCat.id=:catId")
    Optional<List<Product>> findAllProductOfCategory(Long catId);
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCode=:prodCode AND prod.prodPos.id=:posId")
    Optional<Product> findProductInPointofsaleByCode(@Param("prodCode") String prodCode, @Param("posId") Long posId);
    Optional<Product> findProductById(Long prodId);

}
