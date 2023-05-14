package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.SaleInvoiceCapsule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SaleInvoiceCapsuleRepository
        extends JpaRepository<SaleInvoiceCapsule, Long> {
    //Rechercher le SaleInvoiceCapsule a partir de son Id
    Optional<SaleInvoiceCapsule> findSaleInvoiceCapsuleById(Long saleicapsId);
    //Rechercher le SaleInvoiceCapsule a partir du code et du pointofsale
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsCode=:saleicapsCode AND saleicaps.saleicapsPos.id=:posId")
    Optional<SaleInvoiceCapsule> findSaleInvoiceCapsuleByCodeinPos(@Param("saleicapsCode") String saleicapsCode, @Param("posId") Long posId);
    //Faire la liste des SaleInvoiceCapsule dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsBetween(@Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate);
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsBetween(@Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un client dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsUserbm.id=:userbmId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsUserbm.id=:userbmId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                     @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsUserbm.id=:userbmId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsUserbm.id=:userbmId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un client dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsUserbm.id=:userbmId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                                @Param("userbmId") Long userbmId,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsUserbm.id=:userbmId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                                 @Param("userbmId") Long userbmId,
                                                                                 @Param("startDate") Instant startDate,
                                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCapsule d'un client enregistre par un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsUserbm.id=:userbmId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<List<SaleInvoiceCapsule>> findAllSaleicapsofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                     @Param("userbmId") Long userbmId,
                                                                                     @Param("posId") Long posId,
                                                                                     @Param("startDate") Instant startDate,
                                                                                     @Param("endDate") Instant endDate);

    @Query("SELECT saleicaps FROM SaleInvoiceCapsule saleicaps WHERE saleicaps.saleicapsClient.id=:clientId AND saleicaps.saleicapsUserbm.id=:userbmId AND saleicaps.saleicapsPos.id=:posId AND (saleicaps.saleicapsInvoicingDate>=:startDate AND saleicaps.saleicapsInvoicingDate<=:endDate) ORDER BY saleicaps.saleicapsClient.clientName ASC")
    Optional<Page<SaleInvoiceCapsule>> findPageSaleicapsofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                      @Param("userbmId") Long userbmId,
                                                                                      @Param("posId") Long posId,
                                                                                      @Param("startDate") Instant startDate,
                                                                                      @Param("endDate") Instant endDate,
                                                                                      Pageable pageable);



}
