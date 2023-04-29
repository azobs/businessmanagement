package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosCashOperation;
import com.c2psi.businessmanagement.models.ProviderCashOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProviderCashOperationRepository
        extends JpaRepository<ProviderCashOperation, Long> {
    Optional<ProviderCashOperation> findProviderCashOperationById(Long procopId);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId")
    List<ProviderCashOperation> findAllProviderCashOperation(@Param("procaId") Long procaId);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId")
    Page<ProviderCashOperation> findAllProviderCashOperation(@Param("procaId") Long procaId, Pageable pageable);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND procop.pcoOperation.opType=:opType")
    List<ProviderCashOperation> findAllProviderCashOperationOfType(@Param("procaId") Long procaId,
                                                         @Param("opType") OperationType opType);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND procop.pcoOperation.opType=:opType")
    Page<ProviderCashOperation> findPageProviderCashOperationOfType(@Param("procaId") Long procaId,
                                                                   @Param("opType") OperationType opType, Pageable pageable);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND (procop.pcoOperation.opDate>=:startDate AND procop.pcoOperation.opDate<=:endDate)")
    List<ProviderCashOperation> findAllProviderCashOperationBetween(@Param("procaId") Long procaId,
                                                          @Param("startDate") Instant startDate,
                                                          @Param("endDate") Instant endDate);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND (procop.pcoOperation.opDate>=:startDate AND procop.pcoOperation.opDate<=:endDate)")
    Page<ProviderCashOperation> findPageProviderCashOperationBetween(@Param("procaId") Long procaId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate, Pageable pageable);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND procop.pcoOperation.opType=:opType AND (procop.pcoOperation.opDate>=:startDate AND procop.pcoOperation.opDate<=:endDate)")
    List<ProviderCashOperation> findAllProviderCashOperationOfTypeBetween(@Param("procaId") Long procaId,
                                                                @Param("opType") OperationType opType,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate);

    @Query("SELECT procop FROM ProviderCashOperation  procop WHERE procop.pcoProCashAccount.id=:procaId AND procop.pcoOperation.opType=:opType AND (procop.pcoOperation.opDate>=:startDate AND procop.pcoOperation.opDate<=:endDate)")
    Page<ProviderCashOperation> findPageProviderCashOperationOfTypeBetween(@Param("procaId") Long procaId,
                                                                          @Param("opType") OperationType opType,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate,
                                                                           Pageable pageable);
}
