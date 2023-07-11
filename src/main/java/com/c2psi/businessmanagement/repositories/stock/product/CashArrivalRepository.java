package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.models.CashArrival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CashArrivalRepository extends JpaRepository<CashArrival, Long> {
    Optional<CashArrival> findCashArrivalById(Long cashaId);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaArt.id=:articleId AND casha.cashaSicash.id=:sicashId")
    Optional<CashArrival> findCashArrivalByArticleAndSicash(Long articleId, Long sicashId);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId ORDER BY casha.cashaArt.artName ASC ")
    Optional<List<CashArrival>> findAllCashArrivalinSicash(Long sicashId);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId ORDER BY casha.cashaArt.artName ASC ")
    Optional<Page<CashArrival>> findPageCashArrivalinSicash(Long sicashId, Pageable pageable);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND casha.cashaArrivaltype=:cashArrivalType ORDER BY casha.cashaArt.artName ASC ")
    Optional<List<CashArrival>> findAllCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType,  Long sicashId);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND casha.cashaArrivaltype=:cashArrivalType ORDER BY casha.cashaArt.artName ASC ")
    Optional<Page<CashArrival>> findPageCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType,  Long sicashId, Pageable pageable);

    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<List<CashArrival>> findAllCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<Page<CashArrival>> findPageCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND casha.cashaArrivaltype=:cashArrivalType AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<List<CashArrival>> findAllCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType,  Long sicashId, Instant startDate, Instant endDate);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.id=:sicashId AND casha.cashaArrivaltype=:cashArrivalType AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<Page<CashArrival>> findPageCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType,  Long sicashId, Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.sicashPosId=:posId AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<List<CashArrival>> findAllCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate);
    @Query("SELECT casha FROM CashArrival casha WHERE casha.cashaSicash.sicashPosId=:posId AND (casha.cashaArrivalEntryDate>=:startDate AND casha.cashaArrivalEntryDate<=:endDate) ORDER BY casha.cashaArt.artName ASC ")
    Optional<Page<CashArrival>> findPageCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate, Pageable pageable);


}
