package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointofsaleRepository extends JpaRepository<Pointofsale, Long> {
    List<Pointofsale> findAllByPosEnterprise(Enterprise entPos);
    Optional<Pointofsale> findPointofsaleById(Long id);
}
