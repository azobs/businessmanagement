package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface PosCapsuleOperationService {
    PosCapsuleOperationDto updatePosCapsuleOperation(PosCapsuleOperationDto poscsopDto);

    Boolean isPosCapsuleOperationDeleteable(Long poscapsopId);

    Boolean deletePosCapsuleOperationById(Long poscsopId);

    List<PosCapsuleOperationDto> findAllPosCapsuleOperation(Long pcapsaccId);
    List<PosCapsuleOperationDto> findAllPosCapsuleOperationofType(Long pcapsaccId, OperationType opType);

    Page<PosCapsuleOperationDto> findPagePosCapsuleOperation(Long pcapsaccId, int pagenum, int pagesize);

    Page<PosCapsuleOperationDto> findPagePosCapsuleOperationofType(Long pcapsaccId, OperationType opType,
                                                                   int pagenum, int pagesize);

    List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(
            Long pcapsaccId, Instant startDate, Instant endDate);

    Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsaccId, Instant startDate, Instant endDate,
                                                              int pagenum, int pagesize);

    List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(
            Long pcapsaccId, OperationType op_type, Instant startDate, Instant endDate);

    Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsaccId, OperationType op_type,
                                                              Instant startDate, Instant endDate,
                                                              int pagenum, int pagesize);
}
