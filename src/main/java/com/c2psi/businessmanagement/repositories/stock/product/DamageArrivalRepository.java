package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.DamageArrival;
import com.c2psi.businessmanagement.models.DamageArrival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DamageArrivalRepository extends JpaRepository<DamageArrival, Long> {
    Optional<DamageArrival> findDamageArrivalById(Long DamArrId);
    @Query("SELECT dama FROM DamageArrival dama WHERE dama.damaArt.id=:articleId AND dama.damaSidam.id=:sidamId")
    Optional<DamageArrival> findDamArrivalByArticleAndSidam(Long articleId, Long sidamId);
    @Query("SELECT dama FROM DamageArrival dama WHERE dama.damaSidam.id=:siDamId ORDER BY dama.damaArt.artName ASC ")
    Optional<List<DamageArrival>> findAllDamArrivalinSidam(Long siDamId);
    @Query("SELECT dama FROM DamageArrival dama WHERE dama.damaSidam.id=:siDamId ORDER BY dama.damaArt.artName ASC ")
    Optional<Page<DamageArrival>> findPageDamArrivalinSidam(Long siDamId, Pageable pageable);
    @Query("SELECT dama FROM DamageArrival dama WHERE dama.damaSidam.id=:siDamId AND (dama.damaArrivalEntryDate>=:startDate AND dama.damaArrivalEntryDate<=:endDate) ORDER BY dama.damaArt.artName ASC ")
    Optional<List<DamageArrival>> findAllDamArrivalinSidamBetween(Long siDamId, Instant startDate, Instant endDate);
    @Query("SELECT dama FROM DamageArrival dama WHERE dama.damaSidam.id=:siDamId AND (dama.damaArrivalEntryDate>=:startDate AND dama.damaArrivalEntryDate<=:endDate) ORDER BY dama.damaArt.artName ASC ")
    Optional<Page<DamageArrival>> findPageDamArrivalinSidamBetween(Long siDamId, Instant startDate, Instant endDate, Pageable pageable);
}
