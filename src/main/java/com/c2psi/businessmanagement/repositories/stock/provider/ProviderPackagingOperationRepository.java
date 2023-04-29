package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosPackagingOperation;
import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProviderPackagingOperationRepository
        extends JpaRepository<ProviderPackagingOperation, Long> {
    Optional<ProviderPackagingOperation> findProviderPackagingOperationById(Long propackopId);
    @Query("SELECT propackop FROM ProviderPackagingOperation propackop WHERE propackop.propoProPackagingAccount.id=:propackopId")
    List<ProviderPackagingOperation> findAllProviderPackagingOperation(@Param("propackopId") Long propackopId);
    @Query("SELECT propackop FROM ProviderPackagingOperation propackop WHERE propackop.propoProPackagingAccount.id=:propackopId")
    Page<ProviderPackagingOperation> findPageProviderPackagingOperation(@Param("propackopId") Long propackopId, Pageable pageable);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:ppackopId AND propackop.propoOperation.opType=:opType")
    List<ProviderPackagingOperation> findAllProviderPackagingOperationOfType(@Param("ppackopId") Long ppackopId,
                                                                             @Param("opType") OperationType opType);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:ppackopId AND propackop.propoOperation.opType=:opType")
    Page<ProviderPackagingOperation> findPageProviderPackagingOperationOfType(@Param("ppackopId") Long ppackopId,
                                                                              @Param("opType") OperationType opType,
                                                                              Pageable pageable);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:propackopId AND (propackop.propoOperation.opDate>=:startDate AND propackop.propoOperation.opDate<=:endDate)")
    List<ProviderPackagingOperation> findAllProviderPackagingOperationBetween(@Param("propackopId") Long propackopId,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:propackopId AND (propackop.propoOperation.opDate>=:startDate AND propackop.propoOperation.opDate<=:endDate)")
    Page<ProviderPackagingOperation> findPageProviderPackagingOperationBetween(@Param("propackopId") Long propackopId,
                                                                               @Param("startDate") Instant startDate,
                                                                               @Param("endDate") Instant endDate,
                                                                               Pageable pageable);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:propackopId AND propackop.propoOperation.opType=:opType AND (propackop.propoOperation.opDate>=:startDate AND propackop.propoOperation.opDate<=:endDate)")
    List<ProviderPackagingOperation> findAllProviderPackagingOperationOfTypeBetween(@Param("propackopId") Long propackopId,
                                                                                    @Param("opType") OperationType opType,
                                                                                    @Param("startDate") Instant startDate,
                                                                                    @Param("endDate") Instant endDate);
    @Query("SELECT propackop FROM ProviderPackagingOperation  propackop WHERE propackop.propoProPackagingAccount.id=:propackopId AND propackop.propoOperation.opType=:opType AND (propackop.propoOperation.opDate>=:startDate AND propackop.propoOperation.opDate<=:endDate)")
    Page<ProviderPackagingOperation> findPageProviderPackagingOperationOfTypeBetween(@Param("propackopId") Long propackopId,
                                                                                     @Param("opType") OperationType opType,
                                                                                     @Param("startDate") Instant startDate,
                                                                                     @Param("endDate") Instant endDate,
                                                                                     Pageable pageable);
}
