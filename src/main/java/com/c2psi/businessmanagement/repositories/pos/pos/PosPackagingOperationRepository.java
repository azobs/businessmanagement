package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import com.c2psi.businessmanagement.models.PosPackagingOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PosPackagingOperationRepository
        extends JpaRepository<PosPackagingOperation, Long> {
    Optional<PosPackagingOperation> findPosPackagingOperationById(Long ppackopId);
    //liste des operations sur un compte packaging
    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId")
    List<PosPackagingOperation> findAllPosPackagingOperation(@Param("ppackopId") Long ppackopId);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId")
    Page<PosPackagingOperation> findPagePosPackagingOperation(@Param("ppackopId") Long ppackopId, Pageable pageable);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND ppackop.pospoOperation.opType=:opType")
    List<PosPackagingOperation> findAllPosPackagingOperationOfType(@Param("ppackopId") Long ppackopId,
                                                                   @Param("opType") OperationType opType);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND ppackop.pospoOperation.opType=:opType")
    Page<PosPackagingOperation> findPagePosPackagingOperationOfType(@Param("ppackopId") Long ppackopId,
                                                                    @Param("opType") OperationType opType,
                                                                    Pageable pageable);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND (ppackop.pospoOperation.opDate>=:startDate AND ppackop.pospoOperation.opDate<=:endDate)")
    List<PosPackagingOperation> findAllPosPackagingOperationBetween(@Param("ppackopId") Long ppackopId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND (ppackop.pospoOperation.opDate>=:startDate AND ppackop.pospoOperation.opDate<=:endDate)")
    Page<PosPackagingOperation> findPagePosPackagingOperationBetween(@Param("ppackopId") Long ppackopId,
                                                                     @Param("startDate") Instant startDate,
                                                                     @Param("endDate") Instant endDate,
                                                                     Pageable pageable);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND ppackop.pospoOperation.opType=:opType AND (ppackop.pospoOperation.opDate>=:startDate AND ppackop.pospoOperation.opDate<=:endDate)")
    List<PosPackagingOperation> findAllPosPackagingOperationOfTypeBetween(@Param("ppackopId") Long ppackopId,
                                                                          @Param("opType") OperationType opType,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate);

    @Query("SELECT ppackop FROM PosPackagingOperation  ppackop WHERE ppackop.pospoPosPackagingAccount.id=:ppackopId AND ppackop.pospoOperation.opType=:opType AND (ppackop.pospoOperation.opDate>=:startDate AND ppackop.pospoOperation.opDate<=:endDate)")
    Page<PosPackagingOperation> findPagePosPackagingOperationOfTypeBetween(@Param("ppackopId") Long ppackopId,
                                                                           @Param("opType") OperationType opType,
                                                                           @Param("startDate") Instant startDate,
                                                                           @Param("endDate") Instant endDate,
                                                                           Pageable pageable);
}
