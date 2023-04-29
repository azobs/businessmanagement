package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ClientCashOperation;
import com.c2psi.businessmanagement.models.ProviderCashOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ClientCashOperationRepository
        extends JpaRepository<ClientCashOperation, Long> {
    Optional<ClientCashOperation> findClientCashOperationById(Long ccopId);
    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId")
    List<ClientCashOperation> findAllClientCashOperation(@Param("ccaId") Long ccaId);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId")
    Page<ClientCashOperation> findPageClientCashOperation(@Param("ccaId") Long ccaId, Pageable pageable);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND ccaop.ccoOperation.opType=:opType")
    List<ClientCashOperation> findAllClientCashOperationOfType(@Param("ccaId") Long ccaId,
                                                               @Param("opType") OperationType opType);
    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND ccaop.ccoOperation.opType=:opType")
    Page<ClientCashOperation> findPageClientCashOperationOfType(@Param("ccaId") Long ccaId,
                                                                @Param("opType") OperationType opType,
                                                                Pageable pageable);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND (ccaop.ccoOperation.opDate>=:startDate AND ccaop.ccoOperation.opDate<=:endDate)")
    List<ClientCashOperation> findAllClientCashOperationBetween(@Param("ccaId") Long ccaId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND (ccaop.ccoOperation.opDate>=:startDate AND ccaop.ccoOperation.opDate<=:endDate)")
    Page<ClientCashOperation> findPageClientCashOperationBetween(@Param("ccaId") Long ccaId,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate,
                                                                 Pageable pageable);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND ccaop.ccoOperation.opType=:opType AND (ccaop.ccoOperation.opDate>=:startDate AND ccaop.ccoOperation.opDate<=:endDate)")
    List<ClientCashOperation> findAllClientCashOperationOfTypeBetween(@Param("ccaId") Long ccaId,
                                                                      @Param("opType") OperationType opType,
                                                                      @Param("startDate") Instant startDate,
                                                                      @Param("endDate") Instant endDate);

    @Query("SELECT ccaop FROM ClientCashOperation  ccaop WHERE ccaop.ccoCltCashAccount.id=:ccaId AND ccaop.ccoOperation.opType=:opType AND (ccaop.ccoOperation.opDate>=:startDate AND ccaop.ccoOperation.opDate<=:endDate)")
    Page<ClientCashOperation> findPageClientCashOperationOfTypeBetween(@Param("ccaId") Long ccaId,
                                                                      @Param("opType") OperationType opType,
                                                                      @Param("startDate") Instant startDate,
                                                                      @Param("endDate") Instant endDate,
                                                                       Pageable pageable);
}
