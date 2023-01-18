package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
