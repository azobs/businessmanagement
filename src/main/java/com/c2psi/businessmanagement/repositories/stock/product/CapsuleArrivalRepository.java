package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.models.CapsuleArrival;
import com.c2psi.businessmanagement.models.CashArrival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CapsuleArrivalRepository
        extends JpaRepository<CapsuleArrival, Long> {
    Optional<CapsuleArrival> findCapsuleArrivalById(Long capsArrId);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaArt.id=:articleId AND capsa.capsaSicaps.id=:sicapsId")
    Optional<CapsuleArrival> findCapsArrivalByArticleAndSicaps(Long articleId, Long sicapsId);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.id=:sicapsId ORDER BY capsa.capsaArt.artName ASC ")
    Optional<List<CapsuleArrival>> findAllCapsArrivalinSicaps(Long sicapsId);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.id=:sicapsId ORDER BY capsa.capsaArt.artName ASC ")
    Optional<Page<CapsuleArrival>> findPageCapsArrivalinSicaps(Long sicapsId, Pageable pageable);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.id=:sicapsId AND (capsa.capsaArrivalEntryDate>=:startDate AND capsa.capsaArrivalEntryDate<=:endDate) ORDER BY capsa.capsaArt.artName ASC ")
    Optional<List<CapsuleArrival>> findAllCapsArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.id=:sicapsId AND (capsa.capsaArrivalEntryDate>=:startDate AND capsa.capsaArrivalEntryDate<=:endDate) ORDER BY capsa.capsaArt.artName ASC ")
    Optional<Page<CapsuleArrival>> findPageCapsArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.sicapsPosId=:posId AND (capsa.capsaArrivalEntryDate>=:startDate AND capsa.capsaArrivalEntryDate<=:endDate) ORDER BY capsa.capsaArt.artName ASC ")
    Optional<List<CapsuleArrival>> findAllCapsArrivalinPosBetween(Long posId, Instant startDate, Instant endDate);
    @Query("SELECT capsa FROM CapsuleArrival capsa WHERE capsa.capsaSicaps.sicapsPosId=:posId AND (capsa.capsaArrivalEntryDate>=:startDate AND capsa.capsaArrivalEntryDate<=:endDate) ORDER BY capsa.capsaArt.artName ASC ")
    Optional<Page<CapsuleArrival>> findPageCapsArrivalinPosBetween(Long posId, Instant startDate, Instant endDate, Pageable pageable);


}
