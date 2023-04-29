package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import com.c2psi.businessmanagement.models.PosDamageOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PosDamageOperationRepository
        extends JpaRepository<PosDamageOperation, Long> {
    Optional<PosDamageOperation> findPosDamageOperationById(Long posdamId);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId")
    List<PosDamageOperation> findAllPosDamageOperation(@Param("posdamId") Long posdamId);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId")
    Page<PosDamageOperation> findPagePosDamageOperation(@Param("posdamId") Long posdamId, Pageable pageable);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId AND posdamop.posdoOperation.opType=:opType")
    List<PosDamageOperation> findAllPosDamageOperationOfType(@Param("posdamId") Long posdamId,
                                                            @Param("opType") OperationType opType);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId AND posdamop.posdoOperation.opType=:opType")
    Page<PosDamageOperation> findPagePosDamageOperationOfType(@Param("posdamId") Long posdamId,
                                                             @Param("opType") OperationType opType, Pageable pageable);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId AND (posdamop.posdoOperation.opDate>=:startDate AND posdamop.posdoOperation.opDate<=:endDate)")
    List<PosDamageOperation> findAllPosDamageOperationBetween(@Param("posdamId") Long posdamId,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId AND (posdamop.posdoOperation.opDate>=:startDate AND posdamop.posdoOperation.opDate<=:endDate)")
    Page<PosDamageOperation> findPagePosDamageOperationBetween(@Param("posdamId") Long posdamId,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamId AND posdamop.posdoOperation.opType=:opType AND (posdamop.posdoOperation.opDate>=:startDate AND posdamop.posdoOperation.opDate<=:endDate)")
    List<PosDamageOperation> findAllPosDamageOperationOfTypeBetween(@Param("posdamId") Long posdamId,
                                                                   @Param("opType") OperationType opType,
                                                                   @Param("startDate") Instant startDate,
                                                                   @Param("endDate") Instant endDate);

    @Query("SELECT posdamop FROM PosDamageOperation  posdamop WHERE posdamop.posdoPosDamageAccount.id=:posdamopId AND posdamop.posdoOperation.opType=:opType AND (posdamop.posdoOperation.opDate>=:startDate AND posdamop.posdoOperation.opDate<=:endDate)")
    Page<PosDamageOperation> findPagePosDamageOperationOfTypeBetween(@Param("posdamopId") Long posdamopId,
                                                                    @Param("opType") OperationType opType,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate, Pageable pageable);
}
