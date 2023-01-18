package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.ProductFormated;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFormatedRepository
        extends JpaRepository<ProductFormated, Long> {
}
