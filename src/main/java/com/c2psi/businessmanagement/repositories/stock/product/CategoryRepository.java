package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
