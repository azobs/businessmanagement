package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT prod FROM Product  prod WHERE prod.prodCat.id=:catId")
    List<Product> findAllProductOfCategory(Long catId);

}
