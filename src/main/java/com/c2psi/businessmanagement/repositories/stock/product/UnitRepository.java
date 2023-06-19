package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findUnitById(Long unitId);
    @Query("SELECT unit FROM Unit unit WHERE unit.unitName=:unitName AND unit.unitPosId=:posId")
    Optional<Unit> findUnitByUnitnameAndPos(@Param("unitName") String unitName, @Param("posId") Long posId);
    @Query("SELECT unit FROM Unit unit WHERE unit.unitPosId=:posId")
    Optional<List<Unit>> findAllUnitInPos(@Param("posId") Long posId);
    @Query("SELECT unit FROM Unit unit WHERE unit.unitPosId=:posId")
    Optional<Page<Unit>> findPageofUnitInPos(@Param("posId") Long posId, Pageable pageable);
}
