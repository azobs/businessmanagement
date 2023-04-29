package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ClientPackagingOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ClientPackagingOperationRepository
        extends JpaRepository<ClientPackagingOperation, Long> {
    Optional<ClientPackagingOperation> findClientPackagingOperationById(Long cltpackopId);
    @Query("SELECT cltpackop FROM ClientPackagingOperation cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId")
    List<ClientPackagingOperation> findAllClientPackagingOperation(@Param("cltpackopId") Long cltpackopId);
    @Query("SELECT cltpackop FROM ClientPackagingOperation cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId")
    Page<ClientPackagingOperation> findPageClientPackagingOperation(@Param("cltpackopId") Long cltpackopId, Pageable pageable);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND cltpackop.cltpoOperation.opType=:opType")
    List<ClientPackagingOperation> findAllClientPackagingOperationOfType(@Param("cltpackopId") Long cltpackopId,
                                                                         @Param("opType") OperationType opType);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND cltpackop.cltpoOperation.opType=:opType")
    Page<ClientPackagingOperation> findPageClientPackagingOperationOfType(@Param("cltpackopId") Long cltpackopId,
                                                                          @Param("opType") OperationType opType,
                                                                          Pageable pageable);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND (cltpackop.cltpoOperation.opDate>=:startDate AND cltpackop.cltpoOperation.opDate<=:endDate)")
    List<ClientPackagingOperation> findAllClientPackagingOperationBetween(@Param("cltpackopId") Long cltpackopId,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND (cltpackop.cltpoOperation.opDate>=:startDate AND cltpackop.cltpoOperation.opDate<=:endDate)")
    Page<ClientPackagingOperation> findPageClientPackagingOperationBetween(@Param("cltpackopId") Long cltpackopId,
                                                                           @Param("startDate") Instant startDate,
                                                                           @Param("endDate") Instant endDate,
                                                                           Pageable pageable);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND cltpackop.cltpoOperation.opType=:opType AND (cltpackop.cltpoOperation.opDate>=:startDate AND cltpackop.cltpoOperation.opDate<=:endDate)")
    List<ClientPackagingOperation> findAllClientPackagingOperationOfTypeBetween(@Param("cltpackopId") Long cltpackopId,
                                                                                @Param("opType") OperationType opType,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);
    @Query("SELECT cltpackop FROM ClientPackagingOperation  cltpackop WHERE cltpackop.cltpoCltPackagingAccount.id=:cltpackopId AND cltpackop.cltpoOperation.opType=:opType AND (cltpackop.cltpoOperation.opDate>=:startDate AND cltpackop.cltpoOperation.opDate<=:endDate)")
    Page<ClientPackagingOperation> findPageClientPackagingOperationOfTypeBetween(@Param("cltpackopId") Long cltpackopId,
                                                                                 @Param("opType") OperationType opType,
                                                                                 @Param("startDate") Instant startDate,
                                                                                 @Param("endDate") Instant endDate,
                                                                                 Pageable pageable);
}
