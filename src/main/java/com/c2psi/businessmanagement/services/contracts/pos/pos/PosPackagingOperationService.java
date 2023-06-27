package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface PosPackagingOperationService {
    PosPackagingOperationDto updatePosPackagingOperation(PosPackagingOperationDto ppackopDto);
    Boolean isPosPackagingOperationDeleteable(Long ppackopId);
    Boolean deletePosPackagingOperationById(Long ppackopId);
    List<PosPackagingOperationDto> findAllPosPackagingOperation(Long ppackopId);
    List<PosPackagingOperationDto> findAllPosPackagingOperationofType(Long ppackopId, OperationType opType);
    Page<PosPackagingOperationDto> findPagePosPackagingOperation(Long ppackopId, int pagenum, int pagesize);
    Page<PosPackagingOperationDto> findPagePosPackagingOperationofType(Long ppackopId, OperationType opType,
                                                                       int pagenum, int pagesize);
    List<PosPackagingOperationDto> findAllPosPackagingOperationBetween(Long ppackopId, Instant startDate,
                                                                       Instant endDate);
    Page<PosPackagingOperationDto> findPagePosPackagingOperationBetween(Long ppackopId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);
    List<PosPackagingOperationDto> findAllPosPackagingOperationofTypeBetween(Long ppackopId, OperationType op_type,
                                                                       Instant startDate, Instant endDate);

    Page<PosPackagingOperationDto> findPagePosPackagingOperationofTypeBetween(Long ppackopId, OperationType op_type,
                                                                        Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
}
