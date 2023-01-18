package com.c2psi.businessmanagement.repositories.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.PosCashOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PosCashOperationRepository extends JpaRepository<PosCashOperation, Long> {
    Optional<PosCashOperation> findPosCashOperationById(Long opId);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId")
    List<PosCashOperation> findAllPosCashOperation(@Param("pcaId") Long pcaId);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId")
    Page<PosCashOperation> findAllPosCashOperation(@Param("pcaId") Long pcaId, Pageable pageable);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND pco.poscoOperation.opType=:opType")
    List<PosCashOperation> findAllPosCashOperationOfType(@Param("pcaId") Long pcaId,
                                                         @Param("opType") OperationType opType);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND pco.poscoOperation.opType=:opType")
    Page<PosCashOperation> findAllPosCashOperationOfType(@Param("pcaId") Long pcaId,
                                                         @Param("opType") OperationType opType,
                                                         Pageable pageable);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND (pco.poscoOperation.opDate>=:startDate AND pco.poscoOperation.opDate<=:endDate)")
    List<PosCashOperation> findAllPosCashOperationBetween(@Param("pcaId") Long pcaId,
                                                          @Param("startDate") Date startDate,
                                                          @Param("endDate") Date endDate);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND (pco.poscoOperation.opDate>=:startDate AND pco.poscoOperation.opDate<=:endDate)")
    Page<PosCashOperation> findAllPosCashOperationBetween(@Param("pcaId") Long pcaId,
                                                          @Param("startDate") Date startDate,
                                                          @Param("endDate") Date endDate,
                                                          Pageable pageable);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND pco.poscoOperation.opType=:opType AND (pco.poscoOperation.opDate>=:startDate AND pco.poscoOperation.opDate<=:endDate)")
    List<PosCashOperation> findAllPosCashOperationOfTypeBetween(@Param("pcaId") Long pcaId,
                                                                @Param("opType") OperationType opType,
                                                                @Param("startDate") Date startDate,
                                                                @Param("endDate") Date endDate);

    @Query("SELECT pco FROM PosCashOperation  pco WHERE pco.poscoPosCashAccount.id=:pcaId AND pco.poscoOperation.opType=:opType AND (pco.poscoOperation.opDate>=:startDate AND pco.poscoOperation.opDate<=:endDate)")
    Page<PosCashOperation> findAllPosCashOperationOfTypeBetween(@Param("pcaId") Long pcaId,
                                                                @Param("opType") OperationType opType,
                                                                @Param("startDate") Date startDate,
                                                                @Param("endDate") Date endDate,
                                                                Pageable pageable);
}
