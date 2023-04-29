package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PosCapsuleOperationRepository
        extends JpaRepository<PosCapsuleOperation, Long> {
    Optional<PosCapsuleOperation> findPosCapsuleOperationById(Long opId);

    @Query("SELECT pcapso FROM PosCapsuleOperation  pcapso WHERE pcapso.poscsoPosCapsuleAccount.id=:pcapsId")
    List<PosCapsuleOperation> findAllPosCapsuleOperation(@Param("pcapsId") Long pcapsId);

    @Query("SELECT pcapso FROM PosCapsuleOperation  pcapso WHERE pcapso.poscsoPosCapsuleAccount.id=:pcapsId")
    Page<PosCapsuleOperation> findPagePosCapsuleOperation(@Param("pcapsId") Long pcapsId, Pageable pageable);

    @Query("SELECT pcapso FROM PosCapsuleOperation  pcapso WHERE pcapso.poscsoPosCapsuleAccount.id=:pcapsId AND pcapso.poscsoOperation.opType=:opType")
    List<PosCapsuleOperation> findAllPosCapsuleOperationOfType(@Param("pcapsId") Long pcapsId,
                                                               @Param("opType") OperationType opType);

    @Query("SELECT pcapso FROM PosCapsuleOperation  pcapso WHERE pcapso.poscsoPosCapsuleAccount.id=:pcapsId AND pcapso.poscsoOperation.opType=:opType")
    Page<PosCapsuleOperation> findPagePosCapsuleOperationOfType(@Param("pcapsId") Long pcapsId,
                                                                @Param("opType") OperationType opType, Pageable pageable);

    @Query("SELECT pcapop FROM PosCapsuleOperation  pcapop WHERE pcapop.poscsoPosCapsuleAccount.id=:pcapId AND (pcapop.poscsoOperation.opDate>=:startDate AND pcapop.poscsoOperation.opDate<=:endDate)")
    List<PosCapsuleOperation> findAllPosCapsuleOperationBetween(@Param("pcapId") Long pcapId,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate);

    @Query("SELECT pcapop FROM PosCapsuleOperation  pcapop WHERE pcapop.poscsoPosCapsuleAccount.id=:pcapId AND (pcapop.poscsoOperation.opDate>=:startDate AND pcapop.poscsoOperation.opDate<=:endDate)")
    Page<PosCapsuleOperation> findPagePosCapsuleOperationBetween(@Param("pcapId") Long pcapId,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);

    @Query("SELECT pcapop FROM PosCapsuleOperation  pcapop WHERE pcapop.poscsoPosCapsuleAccount.id=:pcapopId AND pcapop.poscsoOperation.opType=:opType AND (pcapop.poscsoOperation.opDate>=:startDate AND pcapop.poscsoOperation.opDate<=:endDate)")
    List<PosCapsuleOperation> findAllPosCapsuleOperationOfTypeBetween(@Param("pcapopId") Long pcapopId,
                                                                      @Param("opType") OperationType opType,
                                                                      @Param("startDate") Instant startDate,
                                                                      @Param("endDate") Instant endDate);

    @Query("SELECT pcapop FROM PosCapsuleOperation  pcapop WHERE pcapop.poscsoPosCapsuleAccount.id=:pcapopId AND pcapop.poscsoOperation.opType=:opType AND (pcapop.poscsoOperation.opDate>=:startDate AND pcapop.poscsoOperation.opDate<=:endDate)")
    Page<PosCapsuleOperation> findPagePosCapsuleOperationOfTypeBetween(@Param("pcapopId") Long pcapopId,
                                                                       @Param("opType") OperationType opType,
                                                                       @Param("startDate") Instant startDate,
                                                                       @Param("endDate") Instant endDate,
                                                                       Pageable pageable);

}
