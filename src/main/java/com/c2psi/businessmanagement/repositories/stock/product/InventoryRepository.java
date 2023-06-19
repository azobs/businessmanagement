package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findInventoryById(Long invId);
    @Query("SELECT inv FROM Inventory inv WHERE inv.invCode=:invCode AND inv.invPosId=:posId")
    Optional<Inventory> findInventoryByCodeinPos(@Param("invCode") String invCode, @Param("posId") Long posId);
    @Query("SELECT inv FROM Inventory inv WHERE inv.invPosId=:posId AND (inv.invDate>=:startDate AND inv.invDate<=:endDate) ORDER BY inv.invCode ASC")
    Optional<List<Inventory>> findAllInventoryinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                           @Param("endDate") Instant endDate);
    @Query("SELECT inv FROM Inventory inv WHERE inv.invPosId=:posId AND (inv.invDate>=:startDate AND inv.invDate<=:endDate) ORDER BY inv.invCode ASC")
    Optional<Page<Inventory>> findPageInventoryinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                            @Param("endDate") Instant endDate, Pageable pageable);
}
