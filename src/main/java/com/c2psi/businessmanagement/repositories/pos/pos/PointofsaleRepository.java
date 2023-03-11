package com.c2psi.businessmanagement.repositories.pos.pos;


import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Pointofsale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointofsaleRepository extends JpaRepository<Pointofsale, Long> {
    List<Pointofsale> findAllByPosEnterprise(Enterprise entPos);
    Optional<Pointofsale> findPointofsaleById(Long id);
    @Query("SELECT pos FROM Pointofsale  pos WHERE pos.posName=:posName AND pos.posEnterprise.id=:entId")
    Optional<Pointofsale> findPointofsaleOfEnterpriseByName(@Param("posName") String posName, @Param("entId") Long entId);

    @Query("SELECT pos FROM Pointofsale  pos WHERE pos.posAddress.email=:posEmail")
    Optional<Pointofsale> findPointofsaleByPosEmail(@Param("posEmail") String posEmail);

}
