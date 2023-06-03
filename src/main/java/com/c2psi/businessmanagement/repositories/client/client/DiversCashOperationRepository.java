package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.DiversCashOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DiversCashOperationRepository extends JpaRepository<DiversCashOperation, Long> {
    Optional<DiversCashOperation> findDiversCashOperationById(Long dcopId);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:dcaId")
    List<DiversCashOperation> findAllDiversCashOperation(@Param("dcaId") Long dcaId);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:dcaId")
    Page<DiversCashOperation> findPageDiversCashOperation(@Param("dcaId") Long dcaId, Pageable pageable);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND dcaop.dcoOperation.opType=:opType")
    List<DiversCashOperation> findAllDiversCashOperationOfType(@Param("ccaId") Long ccaId,
                                                               @Param("opType") OperationType opType);
    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND dcaop.dcoOperation.opType=:opType")
    Page<DiversCashOperation> findPageDiversCashOperationOfType(@Param("ccaId") Long ccaId,
                                                                @Param("opType") OperationType opType,
                                                                Pageable pageable);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND (dcaop.dcoOperation.opDate>=:startDate AND dcaop.dcoOperation.opDate<=:endDate)")
    List<DiversCashOperation> findAllDiversCashOperationBetween(@Param("ccaId") Long ccaId,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND (dcaop.dcoOperation.opDate>=:startDate AND dcaop.dcoOperation.opDate<=:endDate)")
    Page<DiversCashOperation> findPageDiversCashOperationBetween(@Param("ccaId") Long ccaId,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate,
                                                                 Pageable pageable);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND dcaop.dcoOperation.opType=:opType AND (dcaop.dcoOperation.opDate>=:startDate AND dcaop.dcoOperation.opDate<=:endDate)")
    List<DiversCashOperation> findAllDiversCashOperationOfTypeBetween(@Param("ccaId") Long ccaId,
                                                                      @Param("opType") OperationType opType,
                                                                      @Param("startDate") Instant startDate,
                                                                      @Param("endDate") Instant endDate);

    @Query("SELECT dcaop FROM DiversCashOperation  dcaop WHERE dcaop.dcoDiversCashAccount.id=:ccaId AND dcaop.dcoOperation.opType=:opType AND (dcaop.dcoOperation.opDate>=:startDate AND dcaop.dcoOperation.opDate<=:endDate)")
    Page<DiversCashOperation> findPageDiversCashOperationOfTypeBetween(@Param("ccaId") Long ccaId,
                                                                       @Param("opType") OperationType opType,
                                                                       @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate,
                                                                       Pageable pageable);
}
