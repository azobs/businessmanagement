package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import com.c2psi.businessmanagement.models.ProviderDamageOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProviderDamageOperationRepository
        extends JpaRepository<ProviderDamageOperation, Long> {
    Optional<ProviderDamageOperation> findProviderDamageOperationById(Long prodamop);

    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId")
    List<ProviderDamageOperation> findAllProviderDamageOperation(@Param("prodamopId") Long prodamopId);

    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId")
    Page<ProviderDamageOperation> findPageProviderDamageOperation(@Param("prodamopId") Long prodamopId, Pageable pageable);

    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId AND prodamop.prodoOperation.opType=:opType")
    List<ProviderDamageOperation> findAllProviderDamageOperationofType(@Param("prodamopId") Long prodamopId,
                                                                       @Param("opType") OperationType opType);
    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId AND prodamop.prodoOperation.opType=:opType")
    Page<ProviderDamageOperation> findPageProviderDamageOperationofType(@Param("prodamopId") Long prodamopId,
                                                                       @Param("opType") OperationType opType, Pageable pageable);

    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId AND (prodamop.prodoOperation.opDate>=:startDate AND prodamop.prodoOperation.opDate<=:endDate)")
    List<ProviderDamageOperation> findAllProviderDamageOperationBetween(@Param("prodamopId") Long prodamopId,
                                                                        @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate);
    @Query("SELECT prodamop FROM ProviderDamageOperation prodamop WHERE prodamop.prodoProDamageAccount.id=:prodamopId AND (prodamop.prodoOperation.opDate>=:startDate AND prodamop.prodoOperation.opDate<=:endDate)")
    Page<ProviderDamageOperation> findPageProviderDamageOperationBetween(@Param("prodamopId") Long prodamopId,
                                                                        @Param("startDate") Instant startDate,
                                                                        @Param("endDate") Instant endDate, Pageable pageable);

    @Query("SELECT procapop FROM ProviderDamageOperation procapop WHERE procapop.prodoProDamageAccount.id=:prodamopId AND procapop.prodoOperation.opType=:opType AND (procapop.prodoOperation.opDate>=:startDate AND procapop.prodoOperation.opDate<=:endDate)")
    List<ProviderDamageOperation> findAllProviderDamageOperationofTypeBetween(@Param("prodamopId") Long prodamopId,
                                                                                @Param("opType") OperationType opType,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);

    @Query("SELECT procapop FROM ProviderDamageOperation procapop WHERE procapop.prodoProDamageAccount.id=:prodamopId AND procapop.prodoOperation.opType=:opType AND (procapop.prodoOperation.opDate>=:startDate AND procapop.prodoOperation.opDate<=:endDate)")
    Page<ProviderDamageOperation> findPageProviderDamageOperationofTypeBetween(@Param("prodamopId") Long prodamopId,
                                                                              @Param("opType") OperationType opType,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate,
                                                                              Pageable pageable);

}
