package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ClientCapsuleOperation;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ClientCapsuleOperationRepository
        extends JpaRepository<ClientCapsuleOperation, Long> {
    Optional<ClientCapsuleOperation> findClientCapsuleOperationById(Long ccopId);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId")
    List<ClientCapsuleOperation> findAllClientCapsuleOperation(@Param("ccopId") Long ccopId);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId")
    Page<ClientCapsuleOperation> findPageClientCapsuleOperation(@Param("ccopId") Long ccopId, Pageable pageable);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND ccop.cltcsoOperation.opType=:opType")
    List<ClientCapsuleOperation> findAllClientCapsuleOperationofType(@Param("ccopId") Long ccopId,
                                                                     @Param("opType") OperationType opType);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND ccop.cltcsoOperation.opType=:opType")
    Page<ClientCapsuleOperation> findPageClientCapsuleOperationofType(@Param("ccopId") Long ccopId,
                                                                      @Param("opType") OperationType opType,
                                                                      Pageable pageable);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND (ccop.cltcsoOperation.opDate>=:startDate AND ccop.cltcsoOperation.opDate<=:endDate)")
    List<ClientCapsuleOperation> findAllClientCapsuleOperationBetween(@Param("ccopId") Long ccopId,
                                                                      @Param("startDate") Instant startDate,
                                                                      @Param("endDate") Instant endDate);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND (ccop.cltcsoOperation.opDate>=:startDate AND ccop.cltcsoOperation.opDate<=:endDate)")
    Page<ClientCapsuleOperation> findPageClientCapsuleOperationBetween(@Param("ccopId") Long ccopId,
                                                                       @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate,
                                                                       Pageable pageable);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND ccop.cltcsoOperation.opType=:opType AND (ccop.cltcsoOperation.opDate>=:startDate AND ccop.cltcsoOperation.opDate<=:endDate)")
    List<ClientCapsuleOperation> findAllClientCapsuleOperationofTypeBetween(@Param("ccopId") Long ccopId,
                                                                            @Param("opType") OperationType opType,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate);

    @Query("SELECT ccop FROM ClientCapsuleOperation ccop WHERE ccop.cltcsoCltCapsuleAccount.id=:ccopId AND ccop.cltcsoOperation.opType=:opType AND (ccop.cltcsoOperation.opDate>=:startDate AND ccop.cltcsoOperation.opDate<=:endDate)")
    Page<ClientCapsuleOperation> findPageClientCapsuleOperationofTypeBetween(@Param("ccopId") Long ccopId,
                                                                             @Param("opType") OperationType opType,
                                                                             @Param("startDate") Instant startDate,
                                                                             @Param("endDate") Instant endDate,
                                                                             Pageable pageable);
}
