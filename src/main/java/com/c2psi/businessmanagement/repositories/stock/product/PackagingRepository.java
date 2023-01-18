package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackagingRepository extends JpaRepository<Packaging, Long> {
}
