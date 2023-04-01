package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findEnterpriseById(Long entId);
    Optional<Enterprise> findEnterpriseByEntName(String entName);
    Optional<Enterprise> findEnterpriseByEntNiu(String entNiu);
    @Query("SELECT ent FROM Enterprise  ent WHERE ent.entAddress.email=:entEmail")
    Optional<Enterprise> findEnterpriseByEntEmail(@Param("entEmail") String entEmail);
    @Query("SELECT ent FROM Enterprise  ent WHERE ent.entAdmin.id=:userBMId")
    Optional<List<Enterprise>> findAllEnterpriseAdministrateBy(@Param("userBMId") Long userBMId);
}
