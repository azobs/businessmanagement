package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.ClientDamageOperation;
import com.c2psi.businessmanagement.models.ProviderDamageOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ClientDamageOperationRepository
        extends JpaRepository<ClientDamageOperation, Long> {
    Optional<ClientDamageOperation> findClientDamageOperationById(Long cdamop);
    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId")
    List<ClientDamageOperation> findAllClientDamageOperation(@Param("cdamopId") Long cdamopId);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId")
    Page<ClientDamageOperation> findPageClientDamageOperation(@Param("cdamopId") Long cdamopId, Pageable pageable);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND cdamop.cltdoOperation.opType=:opType")
    List<ClientDamageOperation> findAllClientDamageOperationofType(@Param("cdamopId") Long cdamopId,
                                                                   @Param("opType") OperationType opType);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND cdamop.cltdoOperation.opType=:opType")
    Page<ClientDamageOperation> findPageClientDamageOperationofType(@Param("cdamopId") Long cdamopId,
                                                                    @Param("opType") OperationType opType,
                                                                    Pageable pageable);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND (cdamop.cltdoOperation.opDate>=:startDate AND cdamop.cltdoOperation.opDate<=:endDate)")
    List<ClientDamageOperation> findAllClientDamageOperationBetween(@Param("cdamopId") Long cdamopId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND (cdamop.cltdoOperation.opDate>=:startDate AND cdamop.cltdoOperation.opDate<=:endDate)")
    Page<ClientDamageOperation> findPageClientDamageOperationBetween(@Param("cdamopId") Long cdamopId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate,
                                                                     Pageable pegeable);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND cdamop.cltdoOperation.opType=:opType AND (cdamop.cltdoOperation.opDate>=:startDate AND cdamop.cltdoOperation.opDate<=:endDate)")
    List<ClientDamageOperation> findAllClientDamageOperationofTypeBetween(@Param("cdamopId") Long cdamopId,
                                                                              @Param("opType") OperationType opType,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate);

    @Query("SELECT cdamop FROM ClientDamageOperation cdamop WHERE cdamop.cltdoCltDamageAccount.id=:cdamopId AND cdamop.cltdoOperation.opType=:opType AND (cdamop.cltdoOperation.opDate>=:startDate AND cdamop.cltdoOperation.opDate<=:endDate)")
    Page<ClientDamageOperation> findPageClientDamageOperationofTypeBetween(@Param("cdamopId") Long cdamopId,
                                                                          @Param("opType") OperationType opType,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate,
                                                                           Pageable pageable);

}
