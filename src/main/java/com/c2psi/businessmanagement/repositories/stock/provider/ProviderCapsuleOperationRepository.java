package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProviderCapsuleOperationRepository
        extends JpaRepository<ProviderCapsuleOperation, Long> {

    Optional<ProviderCapsuleOperation> findProviderCapsuleOperationById(Long procapsop);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId")
    List<ProviderCapsuleOperation> findAllProviderCapsuleOperation(@Param("procapopId") Long procapopId);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId")
    Page<ProviderCapsuleOperation> findPageProviderCapsuleOperation(@Param("procapopId") Long procapopId, Pageable pageable);


    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND procapop.proscoOperation.opType=:opType")
    List<ProviderCapsuleOperation> findAllProviderCapsuleOperationofType(@Param("procapopId") Long procapopId,
                                                                         @Param("opType") OperationType opType);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND procapop.proscoOperation.opType=:opType")
    Page<ProviderCapsuleOperation> findPageProviderCapsuleOperationofType(@Param("procapopId") Long procapopId,
                                                                         @Param("opType") OperationType opType,
                                                                          Pageable pageable);


    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND (procapop.proscoOperation.opDate>=:startDate AND procapop.proscoOperation.opDate<=:endDate)")
    List<ProviderCapsuleOperation> findAllProviderCapsuleOperationBetween(@Param("procapopId") Long procapopId,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND (procapop.proscoOperation.opDate>=:startDate AND procapop.proscoOperation.opDate<=:endDate)")
    Page<ProviderCapsuleOperation> findPageProviderCapsuleOperationBetween(@Param("procapopId") Long procapopId,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate,
                                                                          Pageable pageable);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND procapop.proscoOperation.opType=:opType AND (procapop.proscoOperation.opDate>=:startDate AND procapop.proscoOperation.opDate<=:endDate)")
    List<ProviderCapsuleOperation> findAllProviderCapsuleOperationofTypeBetween(@Param("procapopId") Long procapopId,
                                                                                @Param("opType") OperationType opType,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);

    @Query("SELECT procapop FROM ProviderCapsuleOperation procapop WHERE procapop.procsoProCapsuleAccount.id=:procapopId AND procapop.proscoOperation.opType=:opType AND (procapop.proscoOperation.opDate>=:startDate AND procapop.proscoOperation.opDate<=:endDate)")
    Page<ProviderCapsuleOperation> findPageProviderCapsuleOperationofTypeBetween(@Param("procapopId") Long procapopId,
                                                                                 @Param("opType") OperationType opType,
                                                                                 @Param("startDate") Instant startDate,
                                                                                 @Param("endDate") Instant endDate,
                                                                                 Pageable pageable);

}
