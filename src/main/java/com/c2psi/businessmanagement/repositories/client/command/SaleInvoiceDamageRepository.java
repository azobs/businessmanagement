package com.c2psi.businessmanagement.repositories.client.command;


import com.c2psi.businessmanagement.models.SaleInvoiceDamage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SaleInvoiceDamageRepository extends JpaRepository<SaleInvoiceDamage, Long> {

    //Rechercher le SaleInvoiceDamage a partir de son Id
    Optional<SaleInvoiceDamage> findSaleInvoiceDamageById(Long saleidamId);
    //Rechercher le SaleInvoiceDamage a partir du code et du pointofsale
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamCode=:saleidamCode AND saleidam.saleidamPos.id=:posId")
    Optional<SaleInvoiceDamage> findSaleInvoiceDamageByCodeinPos(@Param("saleidamCode") String saleidamCode, @Param("posId") Long posId);
    //Faire la liste des SaleInvoiceDamage dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamBetween(@Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate);
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamBetween(@Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un client dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamUserbm.id=:userbmId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamUserbm.id=:userbmId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidaminPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidaminPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                     @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamUserbm.id=:userbmId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamUserbm.id=:userbmId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un client dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamUserbm.id=:userbmId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                                @Param("userbmId") Long userbmId,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamUserbm.id=:userbmId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                                 @Param("userbmId") Long userbmId,
                                                                                 @Param("startDate") Instant startDate,
                                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceDamage d'un client enregistre par un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamUserbm.id=:userbmId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<List<SaleInvoiceDamage>> findAllsaleidamofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                     @Param("userbmId") Long userbmId,
                                                                                     @Param("posId") Long posId,
                                                                                     @Param("startDate") Instant startDate,
                                                                                     @Param("endDate") Instant endDate);

    @Query("SELECT saleidam FROM SaleInvoiceDamage saleidam WHERE saleidam.saleidamClient.id=:clientId AND saleidam.saleidamUserbm.id=:userbmId AND saleidam.saleidamPos.id=:posId AND (saleidam.saleidamInvoicingDate>=:startDate AND saleidam.saleidamInvoicingDate<=:endDate) ORDER BY saleidam.saleidamClient.clientName ASC")
    Optional<Page<SaleInvoiceDamage>> findPagesaleidamofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                      @Param("userbmId") Long userbmId,
                                                                                      @Param("posId") Long posId,
                                                                                      @Param("startDate") Instant startDate,
                                                                                      @Param("endDate") Instant endDate,
                                                                                      Pageable pageable);
    
}
