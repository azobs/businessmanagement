package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface PosDamageOperationService {
    PosDamageOperationDto updatePosDamageOperation(PosDamageOperationDto posdamopDto);

    Boolean isPosDamageOperationDeleteable(Long posdamopId);

    Boolean deletePosDamageOperationById(Long posdamopId);

    List<PosDamageOperationDto> findAllPosDamageOperation(Long posdamopId);
    List<PosDamageOperationDto> findAllPosDamageOperationofType(Long posdamopId, OperationType opType);

    Page<PosDamageOperationDto> findPagePosDamageOperation(Long posdamopId, int pagenum, int pagesize);

    Page<PosDamageOperationDto> findPagePosDamageOperationofType(Long posdamopId, OperationType opType,
                                                                   int pagenum, int pagesize);

    List<PosDamageOperationDto> findAllPosDamageOperationBetween(
            Long posdamopId, Instant startDate, Instant endDate);

    Page<PosDamageOperationDto> findPagePosDamageOperationBetween(Long posdamopId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);

    List<PosDamageOperationDto> findAllPosDamageOperationBetween(
            Long posdamopId, OperationType op_type, Instant startDate, Instant endDate);

    Page<PosDamageOperationDto> findPagePosDamageOperationBetween(Long posdamopId, OperationType op_type,
                                                                    Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);
}
