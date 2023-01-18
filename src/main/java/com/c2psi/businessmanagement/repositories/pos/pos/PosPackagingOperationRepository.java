package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.PosPackagingOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosPackagingOperationRepository
        extends JpaRepository<PosPackagingOperation, Long> {
}
