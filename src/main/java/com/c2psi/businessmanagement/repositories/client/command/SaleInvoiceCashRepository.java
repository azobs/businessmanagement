package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.SaleInvoiceCash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SaleInvoiceCashRepository
        extends JpaRepository<SaleInvoiceCash, Long> {
    //Rechercher le SaleInvoiceCash a partir de son Id
    Optional<SaleInvoiceCash> findSaleInvoiceCashById(Long saleicashId);
    //Rechercher le SaleInvoiceCash a partir du code et du pointofsale
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashCode=:saleicashCode AND saleicash.saleicashPosId=:posId")
    Optional<SaleInvoiceCash> findSaleInvoiceCashByCodeinPos(@Param("saleicashCode") String saleicashCode, @Param("posId") Long posId);
    //Faire la liste des SaleInvoiceCash dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashBetween(@Param("startDate") Instant startDate,
                                                            @Param("endDate") Instant endDate);
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashBetween(@Param("startDate") Instant startDate,
                                                             @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un client dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                            @Param("endDate") Instant endDate);
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofClientBetween(@Param("clientId") Long clientId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashUserbm.id=:userbmId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashUserbm.id=:userbmId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofUserbmBetween(@Param("userbmId") Long userbmId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashUserbm.id=:userbmId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                         @Param("startDate") Instant startDate,
                                                                         @Param("endDate") Instant endDate);
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashUserbm.id=:userbmId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofUserbminPosBetween(@Param("userbmId") Long userbmId, @Param("posId") Long posId,
                                                                         @Param("startDate") Instant startDate,
                                                                         @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un client dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                         @Param("startDate") Instant startDate,
                                                                         @Param("endDate") Instant endDate);

    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofClientinPosBetween(@Param("clientId") Long clientId, @Param("posId") Long posId,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate, Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashUserbm.id=:userbmId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                             @Param("userbmId") Long userbmId,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate);

    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashUserbm.id=:userbmId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofClientforUserbmBetween(@Param("clientId") Long clientId,
                                                                              @Param("userbmId") Long userbmId,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate,
                                                                              Pageable pageable);
    //Faire la liste des SaleInvoiceCash d'un client enregistre par un userBM dans un Pos dans un intervalle de temps puis page par page
    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashUserbm.id=:userbmId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<List<SaleInvoiceCash>> findAllSaleicashofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                  @Param("userbmId") Long userbmId,
                                                                                  @Param("posId") Long posId,
                                                                                  @Param("startDate") Instant startDate,
                                                                                  @Param("endDate") Instant endDate);

    @Query("SELECT saleicash FROM SaleInvoiceCash saleicash WHERE saleicash.saleicashClient.id=:clientId AND saleicash.saleicashUserbm.id=:userbmId AND saleicash.saleicashPosId=:posId AND (saleicash.saleicashInvoicingDate>=:startDate AND saleicash.saleicashInvoicingDate<=:endDate) ORDER BY saleicash.saleicashClient.clientName ASC")
    Optional<Page<SaleInvoiceCash>> findPageSaleicashofClientforUserbminPosBetween(@Param("clientId") Long clientId,
                                                                                   @Param("userbmId") Long userbmId,
                                                                                   @Param("posId") Long posId,
                                                                                   @Param("startDate") Instant startDate,
                                                                                   @Param("endDate") Instant endDate,
                                                                                   Pageable pageable);

}
