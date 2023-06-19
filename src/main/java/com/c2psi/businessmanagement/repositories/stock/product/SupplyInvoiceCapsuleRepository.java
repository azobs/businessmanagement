package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.SupplyInvoiceCapsule;
import com.c2psi.businessmanagement.models.SupplyInvoiceCash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SupplyInvoiceCapsuleRepository
        extends JpaRepository<SupplyInvoiceCapsule, Long> {
    //Retourner SupplyInvoiceCapsule by Id (sicapsId)
    Optional<SupplyInvoiceCapsule> findSupplyInvoiceCapsuleById(Long sicapsId);

    //Retourner SupplyInvoiceCapsule by sicapsCode et posId (sicapsCode, posId)
    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsCode=:sicapsCode AND sicaps.sicapsPosId=:posId")
    Optional<SupplyInvoiceCapsule> findSupplyInvoiceCapsuleByCodeAndPos(@Param("sicapsCode") String sicapsCode, @Param("posId") Long posId);

    /*
    Retourner la liste des SupplyInvoiceCapsule d'un posId enregistrer dans un intervalle de date (posId, intervalDate)
     */
    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<List<SupplyInvoiceCapsule>> findAllSupplyInvoiceCapsuleofPosBetween(@Param("posId") Long posId,
                                                                                 @Param("startDate") Instant startDate,
                                                                                 @Param("endDate") Instant endDate);

    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<Page<SupplyInvoiceCapsule>> findPageSupplyInvoiceCapsuleofPosBetween(@Param("posId") Long posId,
                                                                                  @Param("startDate") Instant startDate,
                                                                                  @Param("endDate") Instant endDate,
                                                                                  Pageable pageable);

    /*
   Retourner la liste des SupplyInvoiceCapsule d'un posId enregistrer dans un intervalle de date par un userbm donnee
   (posId, userbmId, date)
    */
    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsUserbm.id=:userbmId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<List<SupplyInvoiceCapsule>> findAllSupplyInvoiceCapsuleofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                          @Param("userbmId") Long userbmId,
                                                                                          @Param("startDate") Instant startDate,
                                                                                          @Param("endDate") Instant endDate);

    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsUserbm.id=:userbmId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<Page<SupplyInvoiceCapsule>> findPageSupplyInvoiceCapsuleofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                          @Param("userbmId") Long userbmId,
                                                                                          @Param("startDate") Instant startDate,
                                                                                          @Param("endDate") Instant endDate,
                                                                                           Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceCapsule d'un posId enregistrer dans un intervalle de date pour un provider donnee
    (posId, providerId, date)
     */
    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsProvider.id=:providerId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<List<SupplyInvoiceCapsule>> findAllSupplyInvoiceCapsuleofPosandProviderBetween(@Param("posId") Long posId,
                                                                                            @Param("providerId") Long providerId,
                                                                                            @Param("startDate") Instant startDate,
                                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsProvider.id=:providerId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<Page<SupplyInvoiceCapsule>> findPageSupplyInvoiceCapsuleofPosandProviderBetween(@Param("posId") Long posId,
                                                                                             @Param("providerId") Long providerId,
                                                                                             @Param("startDate") Instant startDate,
                                                                                             @Param("endDate") Instant endDate,
                                                                                             Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceCapsule d'un posId enregistrer dans un intervalle de date
    pour un provider donnee et par un userbm donnee (posId, providerId, userbmId, date)
     */
    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsProvider.id=:providerId AND sicaps.sicapsUserbm.id=:userbmId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<List<SupplyInvoiceCapsule>> findAllSupplyInvoiceCapsuleofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                                     @Param("providerId") Long providerId,
                                                                                                     @Param("userbmId") Long userbmId,
                                                                                                     @Param("startDate") Instant startDate,
                                                                                                     @Param("endDate") Instant endDate);

    @Query("SELECT sicaps FROM SupplyInvoiceCapsule sicaps WHERE sicaps.sicapsPosId=:posId AND sicaps.sicapsProvider.id=:providerId AND sicaps.sicapsUserbm.id=:userbmId AND (sicaps.sicapsInvoicingDate>=:startDate AND sicaps.sicapsInvoicingDate<=:endDate) ORDER BY sicaps.sicapsDeliveryDate ASC, sicaps.sicapsCode ASC")
    Optional<Page<SupplyInvoiceCapsule>> findPageSupplyInvoiceCapsuleofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                                      @Param("providerId") Long providerId,
                                                                                                      @Param("userbmId") Long userbmId,
                                                                                                      @Param("startDate") Instant startDate,
                                                                                                      @Param("endDate") Instant endDate,
                                                                                                      Pageable pageable);

}
