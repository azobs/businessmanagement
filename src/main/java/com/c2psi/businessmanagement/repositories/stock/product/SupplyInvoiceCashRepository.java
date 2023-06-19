package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.SupplyInvoiceCash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SupplyInvoiceCashRepository
        extends JpaRepository<SupplyInvoiceCash, Long> {
    //Retourner SupplyInvoiceCash by Id (sicashId)
    Optional<SupplyInvoiceCash> findSupplyInvoiceCashById(Long sicashId);

    //Retourner SupplyInvoiceCash by sicashCode et posId (sicashCode, posId)
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashCode=:sicashCode AND sicash.sicashPosId=:posId")
    Optional<SupplyInvoiceCash> findSupplyInvoiceCashByCodeAndPos(@Param("sicashCode") String sicashCode, @Param("posId") Long posId);

    /*
    Retourner la liste des SupplyInvoiceCash d'un posId enregistrer dans un intervalle de date (posId, intervalDate)
     */
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<List<SupplyInvoiceCash>> findAllSupplyInvoiceCashofPosBetween(@Param("posId") Long posId,
                                                                           @Param("startDate") Instant startDate,
                                                                           @Param("endDate") Instant endDate);
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<Page<SupplyInvoiceCash>> findPageSupplyInvoiceCashofPosBetween(@Param("posId") Long posId,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate,
                                                                            Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceCash d'un posId enregistrer dans un intervalle de date par un userbm donnee
    (posId, userbmId, date)
     */
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashUserbm.id=:userbmId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<List<SupplyInvoiceCash>> findAllSupplyInvoiceCashofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                    @Param("userbmId") Long userbmId,
                                                                                    @Param("startDate") Instant startDate,
                                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashUserbm.id=:userbmId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<Page<SupplyInvoiceCash>> findPageSupplyInvoiceCashofPosandUserbmBetween(@Param("posId") Long posId,
                                                                                     @Param("userbmId") Long userbmId,
                                                                                     @Param("startDate") Instant startDate,
                                                                                     @Param("endDate") Instant endDate,
                                                                                     Pageable pageable);

    /*
    Retourner la liste des SupplyInvoiceCash d'un posId enregistrer dans un intervalle de date pour un provider donnee
    (posId, providerId, date)
     */
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashProvider.id=:providerId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<List<SupplyInvoiceCash>> findAllSupplyInvoiceCashofPosandProviderBetween(@Param("posId") Long posId,
                                                                                      @Param("providerId") Long providerId,
                                                                                      @Param("startDate") Instant startDate,
                                                                                      @Param("endDate") Instant endDate);

    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashProvider.id=:providerId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<Page<SupplyInvoiceCash>> findPageSupplyInvoiceCashofPosandProviderBetween(@Param("posId") Long posId,
                                                                                      @Param("providerId") Long providerId,
                                                                                      @Param("startDate") Instant startDate,
                                                                                      @Param("endDate") Instant endDate,
                                                                                      Pageable pageable);


    /*
    Retourner la liste des SupplyInvoiceCash d'un posId enregistrer dans un intervalle de date
    pour un provider donnee et par un userbm donnee (posId, providerId, userbmId, date)
     */
    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashProvider.id=:providerId AND sicash.sicashUserbm.id=:userbmId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<List<SupplyInvoiceCash>> findAllSupplyInvoiceCashofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                               @Param("providerId") Long providerId,
                                                                                               @Param("userbmId") Long userbmId,
                                                                                               @Param("startDate") Instant startDate,
                                                                                               @Param("endDate") Instant endDate);

    @Query("SELECT sicash FROM SupplyInvoiceCash sicash WHERE sicash.sicashPosId=:posId AND sicash.sicashProvider.id=:providerId AND sicash.sicashUserbm.id=:userbmId AND (sicash.sicashInvoicingDate>=:startDate AND sicash.sicashInvoicingDate<=:endDate) ORDER BY sicash.sicashDeliveryDate ASC, sicash.sicashCode ASC")
    Optional<Page<SupplyInvoiceCash>> findPageSupplyInvoiceCashofPosandProviderAndUserbmBetween(@Param("posId") Long posId,
                                                                                                @Param("providerId") Long providerId,
                                                                                                @Param("userbmId") Long userbmId,
                                                                                                @Param("startDate") Instant startDate,
                                                                                                @Param("endDate") Instant endDate,
                                                                                                Pageable pageable);




}
