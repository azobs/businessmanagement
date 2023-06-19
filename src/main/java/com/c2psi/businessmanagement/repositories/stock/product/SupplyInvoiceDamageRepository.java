package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.SupplyInvoiceDamage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SupplyInvoiceDamageRepository extends JpaRepository<SupplyInvoiceDamage, Long> {
    //Retourner SupplyInvoiceDamage by Id (sidamId)
    Optional<SupplyInvoiceDamage> findSupplyInvoiceDamageById(Long sidamId);

    //Retourner SupplyInvoiceDamage by sidamCode et posId (sidamCode, posId)
    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamCode=:sidamCode AND sidam.sidamPosId=:posId")
    Optional<SupplyInvoiceDamage> findSupplyInvoiceDamageByCodeAndPos(@Param("sidamCode") String sidamCode,
                                                                      @Param("posId") Long posId);

    /*
    Retourner la liste des SupplyInvoiceDamage d'un posId enregistrer dans un intervalle de date (posId, intervalDate)
     */
    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<List<SupplyInvoiceDamage>> findAllSupplyInvoiceDamageofPosBetween(@Param("posId") Long posId,
                                                                               @Param("startDate") Instant startDate,
                                                                               @Param("endDate") Instant endDate);

    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<Page<SupplyInvoiceDamage>> findPageSupplyInvoiceDamageofPosBetween(@Param("posId") Long posId,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate,
                                                                                Pageable pageable);

    /*
   Retourner la liste des SupplyInvoiceDamage d'un posId enregistrer dans un intervalle de date par un userbm donnee
   (posId, userbmId, date)
    */
    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamUserbm.id=:userbmId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<List<SupplyInvoiceDamage>> findAllSupplyInvoiceDamageofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                        @Param("userbmId") Long userbmId,
                                                                                        @Param("startDate") Instant startDate,
                                                                                        @Param("endDate") Instant endDate);

    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamUserbm.id=:userbmId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<Page<SupplyInvoiceDamage>> findPageSupplyInvoiceDamageofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                         @Param("userbmId") Long userbmId,
                                                                                         @Param("startDate") Instant startDate,
                                                                                         @Param("endDate") Instant endDate,
                                                                                         Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceDamage d'un posId enregistrer dans un intervalle de date pour un provider donnee
    (posId, providerId, date)
     */
    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamProvider.id=:providerId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<List<SupplyInvoiceDamage>> findAllSupplyInvoiceDamageofPosandProviderBetween(@Param("posId") Long posId,
                                                                                          @Param("providerId") Long providerId,
                                                                                          @Param("startDate") Instant startDate,
                                                                                          @Param("endDate") Instant endDate);

    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamProvider.id=:providerId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<Page<SupplyInvoiceDamage>> findPageSupplyInvoiceDamageofPosandProviderBetween(@Param("posId") Long posId,
                                                                                           @Param("providerId") Long providerId,
                                                                                           @Param("startDate") Instant startDate,
                                                                                           @Param("endDate") Instant endDate,
                                                                                           Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceDamage d'un posId enregistrer dans un intervalle de date
    pour un provider donnee et par un userbm donnee (posId, providerId, userbmId, date)
     */
    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamProvider.id=:providerId AND sidam.sidamUserbm.id=:userbmId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<List<SupplyInvoiceDamage>> findAllSupplyInvoiceDamageofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                                   @Param("providerId") Long providerId,
                                                                                                   @Param("userbmId") Long userbmId,
                                                                                                   @Param("startDate") Instant startDate,
                                                                                                   @Param("endDate") Instant endDate);

    @Query("SELECT sidam FROM SupplyInvoiceDamage sidam WHERE sidam.sidamPosId=:posId AND sidam.sidamProvider.id=:providerId AND sidam.sidamUserbm.id=:userbmId AND (sidam.sidamInvoicingDate>=:startDate AND sidam.sidamInvoicingDate<=:endDate) ORDER BY sidam.sidamDeliveryDate ASC, sidam.sidamCode ASC")
    Optional<Page<SupplyInvoiceDamage>> findPageSupplyInvoiceDamageofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                                    @Param("providerId") Long providerId,
                                                                                                    @Param("userbmId") Long userbmId,
                                                                                                    @Param("startDate") Instant startDate,
                                                                                                    @Param("endDate") Instant endDate,
                                                                                                    Pageable pageable);


}
